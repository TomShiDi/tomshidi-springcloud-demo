package com.tomshidi.jpa.repository;

import com.tomshidi.jpa.entity.TreePathTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 节点路径信息操作
 *
 * @author tomshidi
 * @since  2024-4-16 11:16:08
 */
public interface TreePathTableRepo extends JpaRepository<TreePathTable, Long> {

    /**
     * 添加一条数据
     *
     * @param treePathTable 路径数据
     */
//    void insert(@Param("treePathTable") TreePathTable treePathTable);

    /**
     * 批量添加
     *
     * @param list 数据集
     */
//    void insertMore(@Param("list") List<TreePathTable> list);

    /**
     * 将指定节点到父节点的距离减一
     *
     * @param descendant   子节点
     * @param ancestorList 父节点列表
     */
    @Query(value = "update pdg.tree_path_table set distance = distance - 1 where descendant = :descendant and ancestor in (:ancestorList)",
            nativeQuery = true)
    @Modifying
    void reduceOneDistance(@Param("descendant") String descendant, @Param("ancestorList") List<String> ancestorList);

    /**
     * 根据当前节点删除路径信息
     *
     * @param descendantList 当前节点列表
     */
    @Query(value = "delete from pdg.tree_path_table where descendant in (:descendantList)",
            nativeQuery = true)
    @Modifying
    void deleteByDescendants(@Param("descendantList") List<String> descendantList);

    /**
     * 根据祖先节点删除路径信息
     *
     * @param ancestorList 祖先节点列表
     */
    @Query(value = "delete from pdg.tree_path_table where ancestor in (:ancestorList)",
            nativeQuery = true)
    @Modifying
    void deleteByAncestors(@Param("ancestorList") List<String> ancestorList);

    /**
     * 查找所有子节点
     *
     * @param currNodeId 当前节点
     * @return 子节点id列表
     */
    @Query(value = "select t.descendant from pdg.tree_path_table t where t.ancestor = :currNodeId order by t.distance",
            nativeQuery = true)
    List<String> findChildNodeIds(@Param("currNodeId") String currNodeId);

    /**
     * 查找所有子节点到当前节点的路径信息
     *
     * @param currNodeId 当前节点
     * @return 路径信息列表
     */
    @Query(value = "select t.id, t.ancestor, t.descendant, t.distance, t.type from pdg.tree_path_table t where t.ancestor = :currNodeId",
            nativeQuery = true)
    List<TreePathTable> findChildTreePath(@Param("currNodeId") String currNodeId);

    /**
     * 根据距离查找子节点id
     *
     * @param currNodeId 当前节点
     * @param distance   距离
     * @return 子节点id列表
     */
    @Query(value = "select t.descendant from pdg.tree_path_table t where t.ancestor = :currNodeId and t.distance = :distance",
            nativeQuery = true)
    List<String> findChildNodeIdByDistance(@Param("currNodeId") String currNodeId, @Param("distance") Integer distance);

    /**
     * 查找最近一级的所有子节点id列表
     *
     * @param currNodeId 当前节点
     * @return 子节点id列表
     */
    @Query(value = "select t.descendant from pdg.tree_path_table t where t.ancestor = :currNodeId and t.distance = 1",
            nativeQuery = true)
    List<String> findClosestChildNodeIds(@Param("currNodeId") String currNodeId);

    /**
     * 查找最近一级的父节点id
     *
     * @param currNodeId 当前节点
     * @return 最近一级父节点id
     */
    @Query(value = "select t.ancestor from pdg.tree_path_table t where t.descendant = :currNodeId and t.distance = 1",
            nativeQuery = true)
    String findClosestParentNodeId(@Param("currNodeId") String currNodeId);

    /**
     * 查找所有父节点id，返回的列表元素按照由远及近排列
     * 例如：6节点的父节点是3，3节点的父节点是1，1节点的父节点是0
     * 那么6节点调用该方法返回的列表即为：[0,1,3]
     *
     * @param currNodeId 当前节点
     * @return 父节点id列表
     */
    @Query(value = "select t.ancestor from pdg.tree_path_table t where t.descendant = :currNodeId and t.distance > 0 order by t.distance desc",
            nativeQuery = true)
    List<String> findAllParentNodeIds(@Param("currNodeId") String currNodeId);

    /**
     * 查找所有当前节点到父节点的路径信息
     *
     * @param currNodeId 当前节点
     * @return 路径信息
     */
    @Query(value = "select t.id, t.ancestor, t.descendant, t.distance, t.type from pdg.tree_path_table t where t.descendant = :currNodeId",
            nativeQuery = true)
    List<TreePathTable> findAllParentTreePath(@Param("currNodeId") String currNodeId);

    /**
     * 获取某个业务的根节点
     *
     * @param type 业务分类
     * @return 根节点列表，兼容多个根节点的情况
     */
    @Query(value = "SELECT T.descendant " +
            "FROM " +
            "( SELECT descendant, COUNT ( * ) OVER ( PARTITION BY descendant ) AS cnt FROM pdg.tree_path_table WHERE \"type\" = :type) T " +
            "WHERE cnt = 1",
            nativeQuery = true)
    List<String> findRootNodeIds(@Param("type") String type);


    /**
     * 查找当前节点的所有子节点，并将同一层的节点拼接为字符串
     *
     * @param currNodeId 当前节点
     * @return list的第一个元素为当前节点，第二个元素为第一层子节点id串，第三个元素为第二层子节点id串，以此类推
     */
    @Query(value = "select string_agg(T.descendant, ',' order by T.descendant desc) from pdg.tree_path_table T where T.ancestor = :currNodeId group by T.distance order by T.distance asc",
            nativeQuery = true)
    List<String> findLevelChildren(@Param("currNodeId") String currNodeId);
}
