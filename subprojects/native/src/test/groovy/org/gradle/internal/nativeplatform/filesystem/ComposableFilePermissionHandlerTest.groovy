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

package org.gradle.internal.nativeplatform.filesystem

import org.gradle.util.TemporaryFolder
import org.jruby.ext.posix.FileStat
import org.junit.Rule
import spock.lang.Specification

class ComposableFilePermissionHandlerTest extends Specification {
    final ComposableFilePermissionHandler.Chmod chmod = Mock()
    final ComposableFilePermissionHandler.Stat stat = Mock()
    final ComposableFilePermissionHandler handler = new ComposableFilePermissionHandler(chmod, stat)

    @Rule TemporaryFolder temporaryFolder;

    def "chmod calls are delegated to Chmod"() {
        setup:
        def file = temporaryFolder.createFile("testfile");
        when:
        handler.chmod(file, 0744);

        then:
        1 * chmod.chmod(file, 0744)
    }

    def "getUnixMode calls are delegated to Stat"() {
        setup:
        FileStat fileStat = Mock()
        def file = temporaryFolder.createFile("testfile");
        1 * stat.stat(file) >> fileStat
        1 * fileStat.mode() >> 0754

        expect:
        handler.getUnixMode(file) == 0754
    }
}
