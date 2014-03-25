/*
 * Autopsy Forensic Browser
 *
 * Copyright 2014 Basis Technology Corp.
 * Contact: carrier <at> sleuthkit <dot> org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sleuthkit.autopsy.fileextmismatch;

import org.sleuthkit.autopsy.ingest.IngestModuleIngestJobSettings;

/**
 * Ingest options for the file extension mismatch detector ingest module.
 */
final class FileExtMismatchDetectorModuleSettings implements IngestModuleIngestJobSettings {

    private boolean skipKnownFiles = false;
    private boolean skipFilesWithNoExtension = true;
    private boolean skipFilesWithTextPlainMimeType = false;

    FileExtMismatchDetectorModuleSettings() {
    }

    FileExtMismatchDetectorModuleSettings(boolean skipKnownFiles, boolean skipFilesWithNoExtension, boolean skipFilesWithTextPlainMimeType) {
        this.skipKnownFiles = skipKnownFiles;
        this.skipFilesWithNoExtension = skipFilesWithNoExtension;
        this.skipFilesWithTextPlainMimeType = skipFilesWithTextPlainMimeType;
    }

    void setSkipKnownFiles(boolean enabled) {
        skipKnownFiles = enabled;
    }

    boolean skipKnownFiles() {
        return skipKnownFiles;
    }

    void setSkipFilesWithNoExtension(boolean enabled) {
        skipFilesWithNoExtension = enabled;
    }

    boolean skipFilesWithNoExtension() {
        return skipFilesWithNoExtension;
    }

    void setSkipFilesWithTextPlainMimeType(boolean enabled) {
        skipFilesWithTextPlainMimeType = enabled;
    }

    boolean skipFilesWithTextPlainMimeType() {
        return skipFilesWithTextPlainMimeType;
    }
}
