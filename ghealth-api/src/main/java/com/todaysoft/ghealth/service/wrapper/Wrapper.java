package com.todaysoft.ghealth.service.wrapper;

import java.util.List;

public interface Wrapper<S, T>
{
    List<T> wrap(List<S> sources);
}
