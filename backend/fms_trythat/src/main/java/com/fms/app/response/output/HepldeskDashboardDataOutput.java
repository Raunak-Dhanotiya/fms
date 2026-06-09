package com.fms.app.response.output;

import java.util.List;

public class HepldeskDashboardDataOutput {

	private List<String> label;
	private List<Long> values;
	private List<String> names;

	/**
	 * @return the label
	 */
	public List<String> getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(List<String> label) {
		this.label = label;
	}

	/**
	 * @return the values
	 */
	public List<Long> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Long> values) {
		this.values = values;
	}
	
	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public HepldeskDashboardDataOutput() {
		// TODO Auto-generated constructor stub
	}

	public HepldeskDashboardDataOutput(List<String> label, List<Long> values, List<String> names) {
		super();
		this.label = label;
		this.values = values;
		this.names = names;
	}

}
