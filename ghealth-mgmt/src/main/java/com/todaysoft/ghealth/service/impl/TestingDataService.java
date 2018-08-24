package com.todaysoft.ghealth.service.impl;

import com.todaysoft.document.generate.sdk.response.ObjectResponse;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.TestingDataUploadRecordDTO;
import com.todaysoft.ghealth.mgmt.model.LocusGenetypeDTO;
import com.todaysoft.ghealth.mgmt.model.OrderTestingDataDTO;
import com.todaysoft.ghealth.mgmt.request.QueryTestingDataUploadRecordsRequest;
import com.todaysoft.ghealth.mgmt.request.TestingDataUploadRequest;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.dataupload.*;
import com.todaysoft.ghealth.model.locus.Locus;
import com.todaysoft.ghealth.service.ILocusService;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.ITestingDataService;
import com.todaysoft.ghealth.service.wrapper.TestingDataUploadRecordWrapper;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.support.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Service
public class TestingDataService implements ITestingDataService
{
    private static Logger log = LoggerFactory.getLogger(TestingDataService.class);

    @Autowired
    private Gateway gateway;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ILocusService locusService;

    @Autowired
    private UploadHandler uploadHandler;

    @Autowired
    private TestingDataUploadRecordWrapper wrapper;

    @Override
    public Pagination<TestingDataUploadRecord> search(TestingDataUploadRecordSearcher searcher, int pageNo, int pageSize)
    {
        QueryTestingDataUploadRecordsRequest request = new QueryTestingDataUploadRecordsRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setUploaderName(searcher.getUploaderName());
        request.setUploadTimeStart(null == searcher.getUploadTimeStart() ? null : searcher.getUploadTimeStart().getTime());
        request.setUploadTimeEnd(null == searcher.getUploadTimeEnd() ? null : searcher.getUploadTimeEnd().getTime());

        PagerResponse<TestingDataUploadRecordDTO> pagerResponse =
            gateway.request("/mgmt/testing-data/upload-records/pager", request, new ParameterizedTypeReference<PagerResponse<TestingDataUploadRecordDTO>>()
            {
            });

        Pager<TestingDataUploadRecordDTO> pager = pagerResponse.getData();
        Pagination<TestingDataUploadRecord> pagination = new Pagination<TestingDataUploadRecord>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }

    @Override
    public TestingDataUploadRecord get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);

        ObjectResponse<TestingDataUploadRecordDTO> response =
            gateway.request("/mgmt/testing-data/upload-records/details", request, new ParameterizedTypeReference<ObjectResponse<TestingDataUploadRecordDTO>>()
            {
            });

        return wrapper.wrap(response.getData());
    }

    @Override
    public TestingDataUploadDetails parse(TestingDataUploadForm data)
    {
        if (StringUtils.isEmpty(data.getTitle()) || null == data.getFile())
        {
            throw new IllegalArgumentException();
        }

        try
        {
            TestingDataUploadFileResolver resolver = new TestingDataUploadFileResolver(data.getFile());
            resolver.resolve();

            List<String> orderCodes = resolver.getOrderCodes();
            List<String> locusNames = resolver.getLocusNames();
            Map<String, String> genetypes = resolver.getGenetypes();

            Map<String, Order> orderCodeMappings = orderService.getOrdersAsCodeMappings(orderCodes);
            Map<String, Locus> locusNameMappings = locusService.getLocusAsNameMappings(locusNames);
            Map<String, List<Order>> specialOrderCodeMappings = orderService.getSpecialOrdersAsCodeMappings(orderCodes);
            Map<String, List<LocusGenetypeDTO>> locusCodeMappings = new HashMap<>();
            List<LocusGenetypeDTO> genetypeDTOList;
            LocusGenetypeDTO locusGenetypeDTO;

            List<TestingDataUploadHeader<Order>> columns = getUploadColumns(orderCodes, orderCodeMappings);
            List<TestingDataUploadHeader<Locus>> rows = getUploadRows(locusNames, locusNameMappings);

            String[] indexs;
            String genetypeText;
            int rowIndex, columnIndex;
            int validGenetypeCount = 0;
            int invalidGenetypeCount = 0;
            TestingDataUploadHeader<Locus> row;
            TestingDataUploadHeader<Order> column;
            List<String> invalidMessages = new ArrayList<String>();
            TestingDataUploadGenetype genetype;
            Map<String, TestingDataUploadGenetype> wrappedGenetypes = new HashMap<String, TestingDataUploadGenetype>();

            for (Map.Entry<String, String> entry : genetypes.entrySet())
            {
                indexs = entry.getKey().split("-");
                rowIndex = Integer.valueOf(indexs[0]);
                columnIndex = Integer.valueOf(indexs[1]);
                genetypeText = entry.getValue().toUpperCase();

                invalidMessages.clear();

                String orderCode = orderCodes.get(columnIndex);
                Order order = orderCodeMappings.get(orderCode);
                MutableObject genetypeMutable = new MutableObject();
                MutableBoolean flag = new MutableBoolean(false);
                genetypeMutable.setValue(genetypeText);

                if (order.getVigilance().equals("1"))
                {
                    //基因型
                    genetypeText = entry.getValue().toUpperCase();
                    locusGenetypeDTO = new LocusGenetypeDTO();
                    locusGenetypeDTO.setLocus(locusNames.get(rowIndex));
                    locusGenetypeDTO.setGenetype(entry.getValue().toUpperCase());
                    genetypeDTOList = new ArrayList<LocusGenetypeDTO>();
                    if (null == locusCodeMappings || !locusCodeMappings.containsKey(orderCode))
                    {
                        genetypeDTOList.add(locusGenetypeDTO);
                    }else {
                        genetypeDTOList.addAll(locusCodeMappings.get(orderCode));
                        genetypeDTOList.add(locusGenetypeDTO);
                    }
                    locusCodeMappings.put(orderCode,genetypeDTOList);

                }
                // 验证 数据上传前后是否一致
                if (!Objects.isNull(order))
                {
                    List<LocusGenetypeDTO> locusGenetypeDTOS = order.getLocusGenetypeDTOS();
                    if (!CollectionUtils.isEmpty(locusGenetypeDTOS))
                    {
                        String locusName = locusNames.get(rowIndex);
                        locusGenetypeDTOS.forEach(x -> {
                            if (locusName.equals(x.getLocus()))
                            {
                                if (!genetypeMutable.getValue().equals(x.getGenetype()))
                                {
                                    invalidMessages.add("两次上传的数据不一致");
                                    flag.setValue(true);
                                }
                            }
                        });
                    }
                }

                row = rows.get(rowIndex);

                if (!row.isValid())
                {
                    invalidMessages.add(row.getMessage());
                }

                column = columns.get(columnIndex);

                if (!column.isValid())
                {
                    invalidMessages.add(column.getMessage());
                }

                if (!Pattern.matches("[AGCT]{2}", genetypeText))
                {
                    invalidMessages.add("无效的基因型");
                }

                genetype = new TestingDataUploadGenetype();
                genetype.setGenetype(genetypeText);

                if (!CollectionUtils.isEmpty(invalidMessages))
                {
                    genetype.setValid(false);
                    genetype.setOverUpload(flag.getValue());
                    genetype.setMessage(StringUtils.join(invalidMessages, " "));
                    invalidGenetypeCount++;
                }
                else
                {
                    genetype.setValid(true);
                    validGenetypeCount++;
                }

                wrappedGenetypes.put(entry.getKey(), genetype);
            }

            Map<String,List<String>> resultMap = new LinkedHashMap<>();
            for ( Map.Entry<String, Order> orderEntry : orderCodeMappings.entrySet())
            {
                if (orderEntry.getValue().getVigilance().equals("1")) {
                    List<Order> specialList;
                    List<List<LocusGenetypeDTO>> orderListLocusList = new ArrayList<List<LocusGenetypeDTO>>();
                    //excel的编号 位点 基因型
                    List<LocusGenetypeDTO> locusGenetypeDTOList = locusCodeMappings.get(orderEntry.getKey());
                    if (specialOrderCodeMappings.containsKey(orderEntry.getKey()))
                    {
                        //库中对比数据
                        specialList = specialOrderCodeMappings.get(orderEntry.getKey());
                        specialList.forEach(x->{
                            orderListLocusList.add(x.getLocusGenetypeDTOS());
                            //订单与excel基因型对比
                            orderListLocusList.forEach(o ->{
                                if (!CollectionUtils.isEmpty(locusGenetypeDTOList)) {
                                    locusGenetypeDTOList.forEach(y -> {
                                        o.forEach(z -> {
                                            if (z.getLocus().equals(y.getLocus())) {
                                                if (!z.getGenetype().equals(y.getGenetype())) {
                                                    String vlueMessage;
                                                    List<String> resultList = new ArrayList<String>();
                                                    if (null == resultMap || !resultMap.containsKey(orderEntry.getKey() + "_" + z.getLocus())) {
                                                        resultList.add("excel样本编号:" + orderEntry.getKey() + "：" + y.getGenetype());
                                                        resultList.add("数据库中样本：" + x.getCode() + "：" + z.getGenetype());
                                                    } else {
                                                        resultList = resultMap.get(orderEntry.getKey() + "_" + z.getLocus());
                                                        vlueMessage = "数据库中样本：" + x.getCode() + "：" + z.getGenetype();
                                                        resultList.add(vlueMessage);
                                                    }
                                                    resultMap.put(orderEntry.getKey() + "_" + z.getLocus(), resultList);
                                                }
                                            }
                                        });
                                    });
                                }
                            });
                            orderListLocusList.clear();
                        });
                    }
                }
            }

            TestingDataUploadDetails details = new TestingDataUploadDetails();
            details.setTitle(data.getTitle());
            details.setFile(data.getFile());
            details.setRows(rows);
            details.setColumns(columns);
            details.setGenetypes(wrappedGenetypes);
            details.setValidGenetypeCount(validGenetypeCount);
            details.setInvalidGenetypeCount(invalidGenetypeCount);
            details.setResultMap(resultMap);
            return details;
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error("Parse testing data file error.", e);
            throw new ServiceException(ErrorCode.EXCEL_RESOLVE_UNDEFINED_ERROR);
        }
    }

    @Override
    public void upload(TestingDataUploadDetails details, boolean ignoreInvalidGenetypes, boolean overUpload)
    {
        if (null == details)
        {
            throw new IllegalStateException();
        }

        if (details.getValidGenetypeCount() == 0)
        {
            throw new IllegalStateException();
        }

        if (!ignoreInvalidGenetypes && details.getInvalidGenetypeCount() != 0)
        {
            throw new IllegalStateException();
        }

        TestingDataUploadRequest request = buildRequest(details, ignoreInvalidGenetypes, overUpload);

        // 保存模板文件
        MultipartFile file = details.getFile();
        try {
            byte[] bytes = file.getBytes();
            request.setFileByte(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        request.setFilename(file.getOriginalFilename());
        gateway.request("/mgmt/testing-data/upload", request);
    }

    private TestingDataUploadRequest buildRequest(TestingDataUploadDetails details, boolean ignoreInvalidGenetypes, boolean overUpload)
    {
        TestingDataUploadHeader<Order> column;
        LocusGenetypeDTO genetype;
        TestingDataUploadGenetype uploadGenetype;
        List<LocusGenetypeDTO> genetypes;
        OrderTestingDataDTO orderTestingData;
        List<OrderTestingDataDTO> orderTestingDatas = new ArrayList<OrderTestingDataDTO>();

        for (int i = 0; i < details.getColumns().size(); i++)
        {
            column = details.getColumns().get(i);

            if (column.isValid())
            {
                genetypes = new ArrayList<LocusGenetypeDTO>();

                Set<String> distinctLocus = new HashSet<>();

                for (int j = 0; j < details.getRows().size(); j++)
                {
                    uploadGenetype = details.getGenetype(j, i);

                    if (uploadGenetype.isValid())
                    {
                        genetype = new LocusGenetypeDTO();
                        genetype.setLocus(details.getRows().get(j).getData().getName());
                        genetype.setGenetype(uploadGenetype.getGenetype());
                        genetypes.add(genetype);
                    }
                    else
                    {
                        if (ignoreInvalidGenetypes)
                        {
                            if (overUpload && uploadGenetype.isOverUpload())
                            {
                                genetype = new LocusGenetypeDTO();
                                genetype.setLocus(details.getRows().get(j).getData().getName());
                                genetype.setGenetype(uploadGenetype.getGenetype());
                                genetypes.add(genetype);
                                distinctLocus.add(genetype.getLocus());
                            }
                            else
                            {
                                continue;
                            }
                        }
                        else
                        {
                            throw new IllegalStateException();
                        }
                    }
                }

                List<LocusGenetypeDTO> mergeList = new ArrayList<>();
                List<LocusGenetypeDTO> locusGenetypeDTOS = column.getData().getLocusGenetypeDTOS();
                if (overUpload)
                {
                    locusGenetypeDTOS = locusGenetypeDTOS.stream().filter(x -> !distinctLocus.contains(x.getLocus())).collect(Collectors.toList());
                }

                if (!CollectionUtils.isEmpty(locusGenetypeDTOS))
                {
                    mergeList.addAll(locusGenetypeDTOS);
                }

                if (!CollectionUtils.isEmpty(genetypes))
                {
                    mergeList.addAll(genetypes);
                    ArrayList<LocusGenetypeDTO> targetDatas = mergeList.stream()
                        .collect(collectingAndThen(
                            toCollection(() -> new TreeSet<>(comparing(LocusGenetypeDTO::getLocus).thenComparing(LocusGenetypeDTO::getGenetype))),
                            ArrayList::new));
                    orderTestingData = new OrderTestingDataDTO();
                    orderTestingData.setOrderId(column.getData().getId());
                    orderTestingData.setGenetypes(targetDatas);
                    orderTestingDatas.add(orderTestingData);
                }
            }
            else
            {
                if (ignoreInvalidGenetypes)
                {
                    continue;
                }
                else
                {
                    throw new IllegalStateException();
                }
            }
        }

        TestingDataUploadRequest request = new TestingDataUploadRequest();
        request.setTitle(details.getTitle());
        request.setOrderGenetypes(orderTestingDatas);
        return request;
    }

    private List<TestingDataUploadHeader<Order>> getUploadColumns(List<String> orderCodes, Map<String, Order> mappings)
    {
        List<TestingDataUploadHeader<Order>> columns = new ArrayList<TestingDataUploadHeader<Order>>();

        Order order;
        TestingDataUploadHeader<Order> column;

        for (String orderCode : orderCodes)
        {
            column = new TestingDataUploadHeader<Order>();
            column.setText(orderCode);
            columns.add(column);

            order = mappings.get(orderCode);

            if (null == order)
            {
                column.setValid(false);
                column.setMessage(MessageFormat.format("系统不存在编号为{0}的订单", orderCode));
            }
            else
            {
                column.setData(order);
                column.setValid(true);
            }
        }

        return columns;
    }

    private List<TestingDataUploadHeader<Locus>> getUploadRows(List<String> locusNames, Map<String, Locus> mappings)
    {
        List<TestingDataUploadHeader<Locus>> rows = new ArrayList<TestingDataUploadHeader<Locus>>();

        Locus locus;
        TestingDataUploadHeader<Locus> row;

        for (String locusName : locusNames)
        {
            row = new TestingDataUploadHeader<Locus>();
            row.setText(locusName);
            rows.add(row);

            locus = mappings.get(locusName);

            if (null == locus)
            {
                row.setValid(false);
                row.setMessage(MessageFormat.format("系统不存在名称为{0}的位点", locusName));
            }
            else
            {
                row.setData(locus);
                row.setValid(true);
            }
        }

        return rows;
    }
}
