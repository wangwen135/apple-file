/**
 *
 */
package com.appleframework.file.sdk.fdfs.codec;

import com.appleframework.file.sdk.fdfs.FileId;

import static com.appleframework.file.sdk.fdfs.FastdfsConstants.Commands.SERVICE_QUERY_UPDATE;

/**
 * 获取可更新的存储服务器
 *
 * @author liulongbiao
 */
public class UpdateStorageGetEncoder extends FileIdOperationEncoder {

    public UpdateStorageGetEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    protected byte cmd() {
        return SERVICE_QUERY_UPDATE;
    }

}
