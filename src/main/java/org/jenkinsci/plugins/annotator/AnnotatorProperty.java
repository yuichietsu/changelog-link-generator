/*
 * The MIT License
 *
 * Copyright 2017 etsu.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jenkinsci.plugins.annotator;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Hudson;
import hudson.model.JobProperty;
import hudson.model.JobPropertyDescriptor;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 *
 * @author etsu
 */
public class AnnotatorProperty extends JobProperty<AbstractProject<?, ?>> {

    public final AnnotatorPattern[] patterns;

    private AnnotatorProperty(AnnotatorPattern[] patterns) {
        this.patterns = patterns;
    }

    @Extension
    public static final class DescriptorImpl extends JobPropertyDescriptor {

        public AnnotatorPattern[] patterns = new AnnotatorPattern[]{};

        public DescriptorImpl() {
            super(AnnotatorProperty.class);
            load();
        }

        @Override
        public JobProperty<?> newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            AnnotatorPattern[] patterns = req.bindParametersToList(AnnotatorPattern.class, "pattern.").toArray(new AnnotatorPattern[]{});
            return new AnnotatorProperty(patterns);
        }

        public static DescriptorImpl get() {
            return Hudson.getInstance().getDescriptorByType(DescriptorImpl.class);
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject o) throws FormException {
            AnnotatorPattern[] patterns = req.bindParametersToList(AnnotatorPattern.class, "pattern.").toArray(new AnnotatorPattern[]{});
            this.patterns = patterns;
            save();
            return super.configure(req, o);
        }

        @Override
        public String getDisplayName() {
            return "Pattern/URL";
        }

        public AnnotatorPattern[] getPatterns() {
            return patterns;
        }
    }

}
