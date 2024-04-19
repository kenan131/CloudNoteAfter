package com.bin.model.dto.socket;


public class SocketDto {
    Integer id;
    String type;
    Integer totalChunks;
    Integer chunkIndex;
    String chunkData;

    public SocketDto() {
    }

    public SocketDto(Integer id, String type, Integer totalChunks, Integer chunkIndex, String chunkData) {
        this.id = id;
        this.type = type;
        this.totalChunks = totalChunks;
        this.chunkIndex = chunkIndex;
        this.chunkData = chunkData;
    }

    @Override
    public String toString() {
        return "Dto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", totalChunks='" + totalChunks + '\'' +
                ", chunkIndex=" + chunkIndex +
                ", chunkData='" + chunkData + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
    }

    public Integer getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(Integer chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public String getChunkData() {
        return chunkData;
    }

    public void setChunkData(String chunkData) {
        this.chunkData = chunkData;
    }
}
