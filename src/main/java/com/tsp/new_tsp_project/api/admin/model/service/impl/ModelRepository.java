package com.tsp.new_tsp_project.api.admin.model.service.impl;

import com.tsp.new_tsp_project.api.admin.model.service.AdminModelJpaDTO;
import com.tsp.new_tsp_project.common.utils.StringUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ModelRepository {

	@PersistenceContext
	private EntityManager em;

	public Integer findModelsCount(Map<String, Object> modelMap) throws Exception {
		return em.createQuery("select m from AdminModelJpaDTO m", AdminModelJpaDTO.class)
				.getResultList().size();
	}

	public List<AdminModelJpaDTO> findAll(Map<String, Object> modelMap) throws Exception{
		return em.createQuery("select m from AdminModelJpaDTO m", AdminModelJpaDTO.class)
				.setFirstResult(StringUtil.getInt(modelMap.get("startPage"),0))
				.setMaxResults(StringUtil.getInt(modelMap.get("size"),0))
				.getResultList();
	}

	public Map<String, Object> findOneModel(AdminModelJpaDTO adminModelJpaDTO) throws Exception {
		TypedQuery<AdminModelJpaDTO> query = em.createQuery("select m from AdminModelJpaDTO m where m.idx = :idx", AdminModelJpaDTO.class);
		query.setParameter("idx", adminModelJpaDTO.getIdx());

		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put("modelInfo", query.getSingleResult());

		return modelMap;
	}
}
