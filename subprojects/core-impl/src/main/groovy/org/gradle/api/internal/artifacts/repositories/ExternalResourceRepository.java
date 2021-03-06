/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.artifacts.repositories;

import org.apache.ivy.plugins.repository.Repository;
import org.gradle.api.Nullable;
import org.gradle.api.internal.externalresource.cached.CachedExternalResource;
import org.gradle.api.internal.externalresource.ExternalResource;
import org.gradle.api.internal.externalresource.metadata.ExternalResourceMetaData;
import org.gradle.api.internal.externalresource.local.LocallyAvailableResourceCandidates;

import java.io.File;
import java.io.IOException;

public interface ExternalResourceRepository extends Repository {

    void downloadResource(ExternalResource resource, File destination) throws IOException;

    ExternalResource getResource(String source) throws IOException;

    ExternalResource getResource(String source, @Nullable LocallyAvailableResourceCandidates localCandidates, @Nullable CachedExternalResource cached) throws IOException;

    /**
     * Fetches only the metadata for the result.
     *
     * @param source The location of the resource to obtain the metadata for
     * @return The resource metadata, or null if the resource does not exist
     * @throws IOException
     */
    @Nullable
    ExternalResourceMetaData getResourceMetaData(String source) throws IOException;
}
