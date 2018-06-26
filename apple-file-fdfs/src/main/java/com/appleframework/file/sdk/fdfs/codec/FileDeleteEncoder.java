/**
 *
 */
package com.appleframework.file.sdk.fdfs.codec;

import com.appleframework.file.sdk.fdfs.FastdfsConstants;
import com.appleframework.file.sdk.fdfs.FileId;

/**
 * 删除请求
 *
 * @author liulongbiao
 */
public class FileDeleteEncoder extends FileIdOperationEncoder {

    public FileDeleteEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    protected byte cmd() {
        return FastdfsConstants.Commands.FILE_DELETE;
    }
}
