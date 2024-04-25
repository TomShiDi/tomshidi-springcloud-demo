package com.tomshidi.jpa.repository;

import com.tomshidi.jpa.entity.ModelParamInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tomshidi
 * @since 2024/4/17 13:39
 */
@Repository
public interface ModelParamInfoRepo extends JpaRepository<ModelParamInfo, String> {
    List<ModelParamInfo> findByModelId(String modelId);

    Integer deleteByModelId(String modelId);
}
