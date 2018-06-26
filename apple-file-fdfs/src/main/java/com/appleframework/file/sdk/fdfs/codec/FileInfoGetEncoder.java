package com.appleframework.file.sdk.fdfs.codec;

import com.appleframework.file.sdk.fdfs.FastdfsConstants;
import com.appleframework.file.sdk.fdfs.FileId;

/**
 * @author siuming
 */
public class FileInfoGetEncoder extends FileIdOperationEncoder {

    /**
     * @param fileId
     */
    public FileInfoGetEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    protected byte cmd() {
        return FastdfsConstants.Commands.FILE_QUERY;
    }
}
