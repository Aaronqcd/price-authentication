package com.pemng.serviceSystem.frame.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.pemng.serviceSystem.base.services.BaseServiceImpl;
import com.pemng.serviceSystem.common.Node;
import com.pemng.serviceSystem.frame.dao.PartMenuDao;
import com.pemng.serviceSystem.frame.services.FrameService;
import com.pemng.serviceSystem.pojo.TPartMenu;

public class FrameServiceImpl extends BaseServiceImpl implements FrameService {

	public PartMenuDao partMenuDao;

	public PartMenuDao getPartMenuDao() {
		return partMenuDao;
	}

	public void setPartMenuDao(PartMenuDao partMenuDao) {
		this.partMenuDao = partMenuDao;
	}

	public static void main(String args[]) {
		FrameServiceImpl test = new FrameServiceImpl();
		test.getNodes(1L);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Node> getNodes(Long userId) {
		//
		HashMap<Long, TPartMenu> menus = new HashMap<Long, TPartMenu>();
		HashMap<String, Long> params = new HashMap<String, Long>();
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct t5 from TUserRoleRel t1, TRoleActn t2, TActnInfo t3, TFnctn t4, TPartMenu t5");
		hql.append(" where t1.TRoleInfo.id = t2.TRoleInfo.id");//用户角色 = 角色动作
		hql.append(" and t2.TActnInfo.id = t3.id");//角色动作 = 动作
		hql.append(" and t3.TFnctn.id = t4.id");//动作 = 功能
		hql.append(" and t4.id = t5.TFnctn.id");//功能 = 菜单
		hql.append(" and t1.TUserInfo.id = :userId");//用户所对应的角色
		hql.append(" order by t5.id asc ");
		
		params.put("userId", userId);
		List<TPartMenu> list = (List<TPartMenu>) partMenuDao.findObjectsByHql(hql.toString(), params);
		for (TPartMenu menu : list) {
			menus.put(menu.getId(), menu);
		}
		// 递归方式获得父级菜单
		for (TPartMenu menu : list) {
			if (!"1".equals(menu.getPartLevel())) {// 非根目录菜单
				putPartMenu(menus, menu);
			}
		}
		// 根栏目
		List<Node> nodes = new ArrayList<Node>();
		List<TPartMenu> menuList = new ArrayList<TPartMenu>();
		menuList.addAll(menus.values());
		Iterator<TPartMenu> iterator = menuList.iterator();
		while (iterator.hasNext()) {
			TPartMenu menu = iterator.next();
			if ("1".equals(menu.getPartLevel())) {
				Node node = new Node();
				node.setId(menu.getId());
				node.setTitle(menu.getPartName());
				node.setUrl(menu.getPartUrl());
				nodes.add(node);
				iterator.remove();
			}
		}
		// 遍历根栏目
		for (Node node : nodes) {
			addNode(node, menuList);
		}
		return nodes;
	}

	/**
	 * 遍历根栏目下的子栏目
	 * 
	 * @param node
	 * @param menuList
	 */
	public void addNode(Node node, List<TPartMenu> menuList) {
		List<Node> children = node.getChildren();
		for (int i = 0; i < menuList.size(); i++) {
			TPartMenu menu = menuList.get(i);
			if (node.getId() == menu.getTPartMenu().getId()) {
				menuList.remove(menu);// 清除
				i--;
				if (children == null) {
					children = new ArrayList<Node>();
				}
				Node node_ = new Node();
				node_.setId(menu.getId());
				node_.setTitle(menu.getPartName());
				node_.setUrl(menu.getPartUrl());
				addNode(node_, menuList);
				children.add(node_);
				node.setChildren(children);
			}
		}
	}

	/**
	 * 递归方式获得父级菜单
	 * 
	 * @param menus
	 * @param partMenu
	 */
	public void putPartMenu(HashMap<Long, TPartMenu> menus, TPartMenu partMenu) {
		if (!"1".equals(partMenu.getPartLevel())) {// 非根目录菜单
			//获得父菜单
			TPartMenu menu = partMenu.getTPartMenu();

			if (menu != null) {
				menus.put(menu.getId(), menu);
				if (!"1".equals(menu.getPartLevel())) {// 非根目录菜单
					putPartMenu(menus, menu);// 递归调用
				}
			}
		}
	}
}
