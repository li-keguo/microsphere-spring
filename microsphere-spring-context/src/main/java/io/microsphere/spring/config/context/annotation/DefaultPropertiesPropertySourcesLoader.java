/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.spring.config.context.annotation;

import io.microsphere.spring.context.annotation.BeanCapableImportCandidate;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.PropertySource;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

import static io.microsphere.spring.core.annotation.AnnotationUtils.getAnnotationAttributes;
import static io.microsphere.spring.core.annotation.GenericAnnotationAttributes.ofSet;

/**
 * The loader of {@link PropertySource} for {@link DefaultPropertiesPropertySources}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @see DefaultPropertiesPropertySources
 * @see DefaultPropertiesPropertySource
 * @see DefaultPropertiesPropertySourceLoader
 * @see AnnotatedPropertySourceLoader
 * @since 1.0.0
 */
class DefaultPropertiesPropertySourcesLoader extends BeanCapableImportCandidate implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = getAnnotationAttributes(metadata, DefaultPropertiesPropertySources.class);

        AnnotationAttributes[] annotationAttributesArray = attributes.getAnnotationArray("value");

        Set<AnnotationAttributes> attributesSet = ofSet(annotationAttributesArray);

        DefaultPropertiesPropertySourceLoader delegate = getDelegate();

        for (AnnotationAttributes elementAttributes : attributesSet) {
            delegate.loadPropertySource(elementAttributes, registry);
        }
    }


    private DefaultPropertiesPropertySourceLoader getDelegate() {
        DefaultPropertiesPropertySourceLoader delegate = new DefaultPropertiesPropertySourceLoader();
        delegate.setEnvironment(getEnvironment());
        delegate.setBeanFactory(getBeanFactory());
        delegate.setResourceLoader(getResourceLoader());
        delegate.setBeanClassLoader(getClassLoader());
        return delegate;
    }
}
