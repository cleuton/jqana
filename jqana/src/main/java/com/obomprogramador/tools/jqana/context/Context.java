/**
 * jQana - Open Source Java(TM) code quality analyzer.
 * 
 * Copyright 2013 Cleuton Sampaio de Melo Jr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Project website: http://www.jqana.com
 */
package com.obomprogramador.tools.jqana.context;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;

import com.obomprogramador.tools.jqana.model.Metric;
import com.obomprogramador.tools.jqana.model.defaultimpl.DefaultMetric;
import com.obomprogramador.tools.jqana.model.defaultimpl.MaxLimitVerificationAlgorithm;

/**
 * jQana - Open Source java source code quality analyzer.
 * 
 * This is the project's context class.
 * 
 * @author Cleuton Sampaio
 * 
 */
public class Context {

    private String currentClassUri;
    private Deque<String> errors;
    private List<Metric> validMetrics;
    private ResourceBundle bundle;
    private String statusBeforeException;

    /**
     * Just return "Status Before Exception".
     * @return Status
     */
    public String getStatusBeforeException() {
        return this.statusBeforeException;
    }

    /**
     * Setter for status.
     * @param statusBeforeException status.
     */
    public void setStatusBeforeException(String statusBeforeException) {
        this.statusBeforeException = statusBeforeException;
    }

    /**
     * Context that receives a bundle.
     * @param bundle ResourceBundle.
     * @throws ClassNotFoundException if the class cannot be found.
     * @throws InstantiationException if there is any problem with instantiation.
     * @throws IllegalAccessException if there is any general problem.
     */
    public Context(ResourceBundle bundle) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        super();
        this.bundle = bundle;
        getMetricConstants();
        this.errors = new ArrayDeque<String>();
    }

    /**
     * Default constructor.
     * @throws ClassNotFoundException if the class cannot be found.
     * @throws InstantiationException if there is any problem with instantiation. 
     * @throws IllegalAccessException if there is any general problem. 
     */
    public Context() throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        this.bundle = ResourceBundle.getBundle("report");
        getMetricConstants();
        this.errors = new ArrayDeque<String>();
    }

    /*
     * (non-Javadoc) I need to think a better way to instantiate metrics in the
     * future. But, for now, this will be enough. Maybe some kind of bean
     * control framework, like Spring.
     */
    private void getMetricConstants() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        this.validMetrics = new ArrayList<Metric>();

        // CC
        Metric ccMetric = new DefaultMetric();
        ccMetric.setMetricName(bundle.getString("metric.cc.name"));
        ccMetric.setMetricMessage(bundle.getString("metric.cc.message"));
        int maxValue = Integer.parseInt(bundle.getString("metric.cc.limit"));
        ccMetric.setVerificationAlgorithm(new MaxLimitVerificationAlgorithm(
                maxValue));
        this.validMetrics.add(ccMetric);

        // LCOM4
        Metric lcom4Metric = new DefaultMetric();
        lcom4Metric.setMetricName(bundle.getString("metric.lcom4.name"));
        lcom4Metric.setMetricMessage(bundle.getString("metric.lcom4.message"));
        maxValue = Integer.parseInt(bundle.getString("metric.lcom4.limit"));
        lcom4Metric.setVerificationAlgorithm(new MaxLimitVerificationAlgorithm(
                maxValue));
        this.validMetrics.add(lcom4Metric);

        // RFC
        Metric rfcMetric = new DefaultMetric();
        rfcMetric.setMetricName(bundle.getString("metric.rfc.name"));
        rfcMetric.setMetricMessage(bundle.getString("metric.rfc.message"));
        maxValue = Integer.parseInt(bundle.getString("metric.rfc.limit"));
        rfcMetric.setVerificationAlgorithm(new MaxLimitVerificationAlgorithm(
                maxValue));
        this.validMetrics.add(rfcMetric);
    }

    /**
     * Getter for the valid metrics list.
     * @return List<Metric> the valid list.
     */
    public List<Metric> getValidMetrics() {
        return validMetrics;
    }

    /**
     * Setter for valid metrics list.
     * @param validMetrics List<Metric> the list. 
     */
    public void setValidMetrics(List<Metric> validMetrics) {
        this.validMetrics = validMetrics;
    }

    /**
     * Return the errors found.
     * @return Deque<String> error list.
     */
    public Deque<String> getErrors() {
        return errors;
    }

    /**
     * Setter for error list.
     * @param errors Deque<String> error list.
     */
    public void setErrors(Deque<String> errors) {
        this.errors = errors;
    }

    /**
     * Get current class' URI.
     * @return String the URI.
     */
    public String getCurrentClassUri() {
        return currentClassUri;
    }

    /**
     * Setter for curent class URI.
     * @param currentClassUri String.
     */
    public void setCurrentClassUri(String currentClassUri) {
        this.currentClassUri = currentClassUri;
    }

    /**
     * Getter for current metric under analysis.
     * @param metricName String.
     * @return Metric the metric.
     */
    public Metric getCurrentMetric(String metricName) {
        Metric metric = new DefaultMetric();
        metric.setMetricName(metricName);
        int index = this.getValidMetrics().indexOf(metric);
        if (index >= 0) {
            metric = this.getValidMetrics().get(index);
        } else {
            metric = null;
        }
        return metric;
    }

    /**
     * Getter for the resource bundle.
     * @return ResourceBundle the bundle.
     */
    public ResourceBundle getBundle() {
        return bundle;
    }

    /**
     * Setter for resource bundle.
     * @param bundle ResourceBundle. 
     */
    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

}
