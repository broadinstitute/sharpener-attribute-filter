package controllers;

import apimodels.ErrorMsg;
import apimodels.GeneInfo;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;
import filter.AttributeFilter;

import play.mvc.Http;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaPlayFrameworkCodegen", date = "2019-11-12T23:40:58.720Z")

public class TransformerApiControllerImp implements TransformerApiControllerImpInterface {
    @Override
    public List<GeneInfo> transformPost(TransformerQuery query) throws Exception {
    	return AttributeFilter.filter(query);
    }

    @Override
    public TransformerInfo transformerInfoGet() throws Exception {
    	return AttributeFilter.transformerInfo();
    }

}
