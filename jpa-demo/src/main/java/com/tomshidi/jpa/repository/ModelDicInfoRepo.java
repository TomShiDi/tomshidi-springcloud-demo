package com.tomshidi.jpa.repository;

import com.tomshidi.jpa.entity.ModelDicInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tomshidi
 * @since 2024/4/17 13:28
 */
@Repository
public interface ModelDicInfoRepo extends JpaRepository<ModelDicInfo, String> {
    /**
     * 根据目录名模糊查找
     *
     * @param dicName  目录名
     * @param pageable 分页对象
     * @return 目录信息列表
     */
    Page<ModelDicInfo> findByDicNameLike(String dicName, Pageable pageable);

    /**
     * 根据目录名查找
     *
     * @param dicName 目录名
     * @return 目录信息
     */
    ModelDicInfo findByDicName(String dicName);

    List<ModelDicInfo> findByIdIn(List<String> dicIdList);

    void deleteByIdIn(List<String> dicIdList);
}
