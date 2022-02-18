package com.supernovacompanies.api.sequence.core.segment;


import com.supernovacompanies.api.sequence.core.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
