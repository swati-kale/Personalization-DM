package com;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class TrainingModel implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("modelName")
	private java.lang.String modelName;
	@org.kie.api.definition.type.Label("confidence")
	private Integer confidence;

	public TrainingModel() {
	}

	public java.lang.String getModelName() {
		return this.modelName;
	}

	public void setModelName(java.lang.String modelName) {
		this.modelName = modelName;
	}

	public java.lang.Integer getConfidence() {
		return this.confidence;
	}

	public void setConfidence(java.lang.Integer confidence) {
		this.confidence = confidence;
	}

	public TrainingModel(java.lang.String modelName,
			java.lang.Integer confidence) {
		this.modelName = modelName;
		this.confidence = confidence;
	}

}