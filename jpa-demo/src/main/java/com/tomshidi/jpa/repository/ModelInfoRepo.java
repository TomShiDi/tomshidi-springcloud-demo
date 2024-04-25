package com.tomshidi.jpa.repository;

import com.tomshidi.jpa.entity.ModelInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tomshidi
 * @since 2024/4/17 13:24
 */
@Repository
public interface ModelInfoRepo extends JpaRepository<ModelInfo, String> {

    Page<ModelInfo> findByDicId(String dicId, Pageable pageable);

    List<ModelInfo> findByModelNameLike(String modelName);

    ModelInfo findByModelName(String modelName);

    void deleteByDicIdIn(List<String> dicIdList);
}
