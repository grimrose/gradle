/*
 * Copyright 2010 the original author or authors.
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
package org.gradle.api.internal.file.copy

import org.gradle.api.internal.file.FileResolver
import org.gradle.util.TemporaryFolder
import org.gradle.util.TestFile
import org.junit.Rule
import spock.lang.Specification
import org.gradle.api.internal.file.BaseDirFileResolver
import org.gradle.internal.nativeplatform.filesystem.FileSystems
import org.gradle.api.file.UnableToDeleteFileException

/**
 * @author Hans Dockter
 */
class DeleteActionImplTest extends Specification {
    @Rule
    TemporaryFolder tmpDir = new TemporaryFolder();

    FileResolver fileResolver = new BaseDirFileResolver(FileSystems.default, tmpDir.getDir())
    
    DeleteActionImpl delete = new DeleteActionImpl(fileResolver);

    TestFile file(String path) {
        tmpDir.file(path)
    }

    def deletesDirectory() {
        TestFile dir = tmpDir.getDir();
        dir.file("somefile").createFile();

        when:
        boolean didWork = delete.delete(dir);

        then:
        dir.assertDoesNotExist();
        didWork
    }

    def deletesFile() {
        TestFile dir = tmpDir.getDir();
        TestFile file = dir.file("somefile");
        file.createFile();

        when:
        boolean didWork = delete.delete(file);

        then:
        file.assertDoesNotExist();
        didWork
    }

    def deletesFileByPath() {
        TestFile dir = tmpDir.getDir();
        TestFile file = dir.file("somefile");
        file.createFile();

        when:
        boolean didWork = delete.delete('somefile');

        then:
        file.assertDoesNotExist();
        didWork
    }

    def deletesMultipleTargets() {
        TestFile file = tmpDir.getDir().file("somefile").createFile();
        TestFile dir = tmpDir.getDir().file("somedir").createDir();
        dir.file("sub/child").createFile();

        when:
        boolean didWork = delete.delete(file, dir);

        then:
        file.assertDoesNotExist();
        dir.assertDoesNotExist();
        didWork
    }

    def didWorkIsFalseWhenNothingDeleted() {
        TestFile dir = tmpDir.file("unknown");
        dir.assertDoesNotExist();

        when:
        boolean didWork = delete.delete(dir);

        then:
        !didWork
    }

}
