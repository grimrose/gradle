/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.tooling.internal.protocol;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Drastic changes to this interface may break the cross-version protocol.
 * If you change it, make sure you run the all tooling api tests to flush out compatibility issues.
 */
public interface InternalLongRunningOperationParameters {
    /**
     * Returns the output stream to write stdout logging to.
     *
     * @return The output stream. May be null.
     */
    OutputStream getStandardOutput();

    /**
     * Returns the output stream to write stderr logging to.
     *
     * @return The output stream. May be null.
     */
    OutputStream getStandardError();

    /**
     * Returns the listener to receive progress events.
     *
     * @return The listener. Must not be null.
     */
    ProgressListenerVersion1 getProgressListener();

    /**
     * Returns the standard input stream to be used.
     *
     * @return The input stream. May be null.
     */
    InputStream getStandardInput();
}