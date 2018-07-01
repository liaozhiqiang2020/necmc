package com.sv.mc.service;

import java.util.List;

public interface BaseService<T> {

    List<T> findAllEntities();

}
