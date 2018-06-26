/**
 *
 */
package com.appleframework.file.sdk.fdfs.codec;

import com.appleframework.file.sdk.fdfs.FileId;

import static com.appleframework.file.sdk.fdfs.FastdfsConstants.Commands.SERVICE_QUERY_FETCH_ONE;

/**
 * 获取可下载的存储服务器
 *
 * @author liulongbiao
 */
public class DownloadStorageGetEncoder extends FileIdOperationEncoder {

    public DownloadStorageGetEncoder(FileId fileId) {
        super(fileId);
    }

    @Override
    protected byte cmd() {
        return SERVICE_QUERY_FETCH_ONE;
    }

}
