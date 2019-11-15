package filter;

import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;

import apimodels.Attribute;
import apimodels.GeneInfo;
import apimodels.Property;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;

public class AttributeFilter {


	private static String ATTRIBUTE_NAME = "attribute name";
	private static String OPERAND = "operand";
	private static String ATTRIBUTE_VALUE = "attribute value";


	private static ObjectMapper mapper = new ObjectMapper();
	
	static {
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	
	public static TransformerInfo transformerInfo() {
		try {
			String json = new String(Files.readAllBytes(Paths.get("transformer_info.json")));
			TransformerInfo info = mapper.readValue(json, TransformerInfo.class);
			ATTRIBUTE_NAME = info.getParameters().get(0).getName();
			OPERAND = info.getParameters().get(1).getName();
			ATTRIBUTE_VALUE = info.getParameters().get(2).getName();
			return info;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static ArrayList<GeneInfo> filter(TransformerQuery query) throws Exception {
		Filter filter = getFilter(query);
		ArrayList<GeneInfo> genes = new ArrayList<GeneInfo>();
		for (GeneInfo gene : query.getGenes()) {
			if (filter.filter(gene)) {
				genes.add(gene);
			}
		}
		return genes;
	}


	private static Filter getFilter(TransformerQuery query) throws Exception {
		String operand = getOperand(query);
		if ("==".equals(operand)) {
			return new EqualFilter(query);
		}
		if ("!=".equals(operand)) {
			return new NonEqualFilter(query);
		}
		throw new IllegalQueryException("wrong opperand: "+operand);
	}


	private static String getOperand(TransformerQuery query) throws Exception {
		for (Property property : query.getControls()) {
			if (OPERAND.equals(property.getName())) {
				return property.getValue();
			}
		}
		throw new IllegalQueryException("operand not specified");
	}


	private static abstract class Filter {

		protected String attributeName = null;
		protected String attributeValue = null;


		Filter(TransformerQuery query) throws Exception {
			for (Property property : query.getControls()) {
				if (ATTRIBUTE_NAME.equals(property.getName())) {
					attributeName = property.getValue();
				}
				if (ATTRIBUTE_VALUE.equals(property.getName())) {
					attributeValue = property.getValue();
				}
			}
			if (attributeName == null) {
				throw new IllegalQueryException("attribute name not specified");
			}
			if (attributeValue == null) {
				throw new IllegalQueryException("attribute value not specified");
			}
		}


		String attributeValue(GeneInfo gene) {
			if (attributeName == null) {
				return null;
			}
			for (Attribute attribute : gene.getAttributes()) {
				if (attributeName.equals(attribute.getName())) {
					return attribute.getValue();
				}
			}
			return null;
		}

		abstract boolean filter(GeneInfo gene);
	}


	private static class EqualFilter extends Filter {

		EqualFilter(TransformerQuery query) throws Exception {
			super(query);
		}


		boolean filter(GeneInfo gene) {
			return attributeValue != null && attributeValue(gene) != null && attributeValue.equals(attributeValue(gene));
		}
	}


	private static class NonEqualFilter extends Filter {

		NonEqualFilter(TransformerQuery query) throws Exception {
			super(query);
		}


		boolean filter(GeneInfo gene) {
			return attributeValue != null && attributeValue(gene) != null && !attributeValue.equals(attributeValue(gene));
		}
	}


	@SuppressWarnings("serial")
	public static class IllegalQueryException extends Exception {
		IllegalQueryException(String message) {
			super(message);
		}
	}
}
