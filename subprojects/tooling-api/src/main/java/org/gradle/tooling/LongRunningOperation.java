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

package org.gradle.tooling;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Offers ways to communicate both ways with a gradle operation, be it building a model or running tasks.
 * <p>
 * Enables tracking progress via listeners that will receive events from the gradle operation.
 * <p>
 * Allows providing standard output streams that will receive output if the gradle operation writes to standard streams.
 * <p>
 * Allows providing standard input that can be consumed by the gradle operation (useful for interactive builds).
 * <p>
 * Enables configuring the build run / model request with options like the java home or jvm arguments.
 * Those settings might not be supported by the target Gradle version. Refer to javadoc for those methods
 * to understand what kind of exception throw and when is it thrown.
 */
public interface LongRunningOperation {

    /**
     * Sets the {@link java.io.OutputStream} which should receive standard output logging generated while running the operation.
     * The default is to discard the output.
     *
     * @param outputStream The output stream.
     * @return this
     */
    LongRunningOperation setStandardOutput(OutputStream outputStream);

    /**
     * Sets the {@link OutputStream} which should receive standard error logging generated while running the operation.
     * The default is to discard the output.
     *
     * @param outputStream The output stream.
     * @return this
     */
    LongRunningOperation setStandardError(OutputStream outputStream);

    /**
     * If the target gradle version supports it you can use this setting
     * to set the standard {@link java.io.InputStream} that will be used by builds.
     * Useful when the tooling api drives interactive builds.
     * <p>
     * If the target gradle version does not support it the long running operation will fail eagerly with
     * {@link org.gradle.tooling.exceptions.UnsupportedOperationConfigurationException} when the operation is started.
     * <p>
     * If not configured or null passed the dummy input stream with zero bytes is used to avoid the build hanging problems.
     *
     * @param inputStream The input stream
     * @return this
     * @since 1.0-milestone-8
     */
    LongRunningOperation setStandardInput(InputStream inputStream);

    /**
     * If the target gradle version supports it you can use this setting
     * to specify the java home directory to use for the long running operation.
     * <p>
     * If the target gradle version does not support it the long running operation will fail eagerly with
     * {@link org.gradle.tooling.exceptions.UnsupportedOperationConfigurationException} when the operation is started.
     * <p>
     * {@link org.gradle.tooling.model.build.BuildEnvironment} model contains information such as java or gradle environment.
     * If you want to get hold of this information you can ask tooling API to build this model.
     * <p>
     * If not configured or null passed the sensible default will be used.
     *
     * @param javaHome to use for the gradle process
     * @return this
     * @since 1.0-milestone-8
     * @throws IllegalArgumentException when supplied javaHome is not a valid folder.
     */
    LongRunningOperation setJavaHome(File javaHome) throws IllegalArgumentException;

    /**
     * If the target gradle version supports it you can use this setting
     * to specify the java vm arguments to use for the long running operation.
     * <p>
     * If the target gradle version does not support it the long running operation will fail eagerly with
     * {@link org.gradle.tooling.exceptions.UnsupportedOperationConfigurationException} when the operation is started.
     * <p>
     * {@link org.gradle.tooling.model.build.BuildEnvironment} model contains information such as java or gradle environment.
     * If you want to get hold of this information you can ask tooling API to build this model.
     * <p>
     * If not configured, null an empty array passed then the reasonable default will be used.
     *
     * @param jvmArguments to use for the gradle process
     * @return this
     * @since 1.0-milestone-9
     */
    LongRunningOperation setJvmArguments(String... jvmArguments);

    /**
     * Specify the command line build arguments. Useful mostly for running tasks via {@link BuildLauncher}.
     * <p>
     * Be aware that not all of the Gradle command line options are supported!
     * Only the build arguments that configure the build execution are supported.
     * They are modelled in the Gradle API via {@link org.gradle.StartParameter}.
     * Examples of supported build arguments: '--info', '-u', '-p'.
     * The command line instructions that are actually separate commands (like '-?', '-v') are not supported.
     * Some other instructions like '--daemon' are also not supported - the tooling API always runs with the daemon.
     * <p>
     * If you specify unknown or unsupported command line option the {@link org.gradle.tooling.exceptions.UnsupportedBuildArgumentException}
     * will be thrown but only at the time when you execute the operation, i.e. {@link BuildLauncher#run()} or {@link ModelBuilder#get()}.
     * <p>
     * For the list of all Gradle command line options please refer to the user guide
     * or take a look at the output of the 'gradle -?' command. Supported arguments are those modelled by
     * {@link org.gradle.StartParameter}.
     * <p>
     * The arguments can potentially override some other settings you have configured.
     * For example, the project directory or Gradle user home directory that are configured
     * in the {@link GradleConnector}.
     * Also, the task names configured by {@link BuildLauncher#forTasks(String...)} can be overridden
     * if you happen to specify other tasks via the build arguments.
     * <p>
     * See the example in the docs for {@link BuildLauncher}
     *
     * @param arguments gradle command line arguments
     * @return this
     * @since 1.0-rc-1
     */
    LongRunningOperation withArguments(String ... arguments);

    /**
     * Adds a progress listener which will receive progress events as the operation runs.
     *
     * @param listener The listener
     * @return this
     */
    LongRunningOperation addProgressListener(ProgressListener listener);

}
