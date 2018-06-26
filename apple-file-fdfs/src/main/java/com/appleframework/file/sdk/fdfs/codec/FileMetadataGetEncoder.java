/**
 *
 */
package com.appleframework.file.sdk.fdfs.codec;

import com.appleframework.file.sdk.fdfs.FastdfsConstants;
import com.appleframework.file.sdk.fdfs.FileId;

/**
 * 获取文件属性请求
 *
 * @author liulongbiao
 */
public class FileMetadataGetEncoder extends FileIdOperationEncoder {

    public FileMetadataGetEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    public byte cmd() {
        return FastdfsConstants.Commands.METADATA_GET;
    }

}