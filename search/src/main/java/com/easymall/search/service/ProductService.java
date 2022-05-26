package com.easymall.search.service;

import easymall.easymallcommon.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;


public interface ProductService {

    boolean saveProductAsIndices(List<SkuEsModel> skuEsModelList) throws IOException;

}
