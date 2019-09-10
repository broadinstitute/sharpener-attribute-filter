package filter;

import java.util.ArrayList;

import apimodels.Attribute;
import apimodels.GeneInfo;
import apimodels.Parameter;
import apimodels.Property;
import apimodels.TransformerInfo;
import apimodels.TransformerQuery;

public class AttributeFilter {

	private static final String TRANSFORMER_NAME = "Attribute filter";
	private static final String ATTRIBUTE_NAME = "attribute name";
	private static final String ATTRIBUTE_VALUE = "attribute value";
	private static final String OPERAND = "operand";


	public static TransformerInfo transformerInfo() {
		TransformerInfo transformerInfo = new TransformerInfo().name(TRANSFORMER_NAME);
		transformerInfo.function(TransformerInfo.FunctionEnum.FILTER);
		transformerInfo.description("Remove genes according to their attribute values");
		transformerInfo.addParametersItem(new Parameter().name(ATTRIBUTE_NAME).type(Parameter.TypeEnum.STRING));
		transformerInfo.addParametersItem(new Parameter().name(OPERAND).type(Parameter.TypeEnum.STRING)
				.addAllowedValuesItem("==").addAllowedValuesItem("!=")._default("=="));
		transformerInfo.addParametersItem(new Parameter().name(ATTRIBUTE_VALUE).type(Parameter.TypeEnum.STRING));
		transformerInfo.addRequiredAttributesItem(".gene_id");
		return transformerInfo;
	}


	public static ArrayList<GeneInfo> filter(TransformerQuery query) {
		Filter filter = getFilter(query);
		ArrayList<GeneInfo> genes = new ArrayList<GeneInfo>();
		for (GeneInfo gene : query.getGenes()) {
			if (filter.filter(gene)) {
				genes.add(gene);
			}
		}
		return genes;
	}


	private static Filter getFilter(TransformerQuery query) {
		String operand = getOperand(query);
		if ("==".equals(operand)) {
			return new EqualFilter(query);
		}
		if ("!=".equals(operand)) {
			return new NonEqualFilter(query);
		}
		return new Filter(query) {
			boolean filter(GeneInfo gene) {
				return false;
			}
		};
	}


	private static String getOperand(TransformerQuery query) {
		for (Property property : query.getControls()) {
			if (OPERAND.equals(property.getName())) {
				return property.getValue();
			}
		}
		return null;
	}


	private static abstract class Filter {

		protected String attributeName = null;
		protected String attributeValue = null;


		Filter(TransformerQuery query) {
			for (Property property : query.getControls()) {
				if (ATTRIBUTE_NAME.equals(property.getName())) {
					attributeName = property.getValue();
				}
				if (ATTRIBUTE_VALUE.equals(property.getName())) {
					attributeValue = property.getValue();
				}
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

		EqualFilter(TransformerQuery query) {
			super(query);
		}


		boolean filter(GeneInfo gene) {
			return attributeValue != null && attributeValue(gene) != null && attributeValue.equals(attributeValue(gene));
		}
	}


	private static class NonEqualFilter extends Filter {

		NonEqualFilter(TransformerQuery query) {
			super(query);
		}


		boolean filter(GeneInfo gene) {
			return attributeValue != null && attributeValue(gene) != null && !attributeValue.equals(attributeValue(gene));
		}
	}

}
