package com.pemng.serviceSystem.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XMLUtils {

	/**
	 * 获得xml的根节点 
	 * @param xmlPath
	 * @return Element
	 */
	public Element getRoot(String xmlPath) {
		Element root = null;
		try {
			SAXReader reader = new  SAXReader();
			Document  document = reader.read(this.getClass().getResourceAsStream(xmlPath));
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return root;  
	}
	
	/**
	 * 根据节点和子节点的名字，获得所有的子节点
	 * @param element
	 * @param childEleName
	 * @return
	 */
	public List getElements(Element element , String childEleName) {
		List elements = element.elements(childEleName);
		return elements;
	}
	
	
	
	public Element getElement(Element element , String childEleName , int index) {
		List elements = element.elements(childEleName);
		if(index < 0)
			index = 0;
		return (Element)elements.get(index);
		
	}
	
	public Element getElement(Element element , String childEleName) {
		return this.getElement(element, childEleName , -1);
	}
	
	public List getAllAttributeName(Element element) {
		List attrNameList = null;
		List attrList = null;
		attrList = element.attributes();
		for(int i =0; i < attrList.size(); i++){
			Attribute attr = (Attribute) attrList.get(i);
			attrNameList.add(attr.getName());
		}
		return attrNameList;
	}
	
	public Map getAttributeNameAndValue(Element element) {
		List attrList = element.attributes();
		Map map = new HashMap();
		for(int i =0; i < attrList.size(); i++) {
			Attribute attr = (Attribute) attrList.get(i);
			map.put(attr.getName(), attr.getText());
		}
		return map;
	}
	
	public Map getAttributeNameAndValue(Element element , List attrList) {
		Map map = new HashMap();
		for(int i =0; i < attrList.size(); i++) {
			String attr = (String) attrList.get(i);
			map.put(attr, element.attribute(attr).getText());
		}
		return map;
	}
	
	/**
	 * 获得所有子节点的属性
	 * @param element
	 * @param childEleName
	 * @return
	 */
	public List getAllElementAttr(Element element, String childEleName) {
		List elements = this.getElements(element, childEleName);
		List attrs = new ArrayList();
		for(int i = 0; i < elements.size(); i ++) {
			Element ele = (Element) elements.get(i);
			attrs.add(this.getAttributeNameAndValue(ele));
			
		}
		return attrs;
	}
	
	
	
	public String getValueByAttrName(Element element , String attrName) {
		Attribute attribute = element.attribute(attrName);
		if(attribute == null){
			return null;
		}
		return attribute.getText();
	}
	public Element getElementByCode(Element element , String childEleName , String code) {
		Element ele = null;
		Attribute attr = null;
		List elements = this.getElements(element, childEleName);
		for(int i = 0; i < elements.size(); i ++) {
			Element ele1 = (Element) elements.get(i);
			attr = ele1.attribute("code");
			if(code != null && code.equals(attr.getText())) {
				ele = ele1;
				break;
			}
		}
		
		return ele;
	}
	
	public String getIdByName(Element element , String childEleName , String name) {
		String id = null;
		Attribute attr = null;
		List elements = this.getElements(element, childEleName);
		for(int i = 0; i < elements.size(); i ++) {
			Element ele1 = (Element) elements.get(i);
			attr = ele1.attribute("name");
			if(name != null && name.equals(attr.getText())) {
				id = ele1.attribute("id").getText();
				break;
			}
		}
		return id;
	}
	
	public String getIdByNameOrId(Element element , String childEleName , String nameOrId) {
		String id = null;
		List elements = this.getElements(element, childEleName);
		for(int i = 0; i < elements.size(); i ++) {
			Element ele1 = (Element) elements.get(i);
			Attribute attr_name = ele1.attribute("name");
			Attribute attr_id = ele1.attribute("id");
			if(nameOrId != null && (nameOrId.equals(attr_name.getText()) || nameOrId.equals(attr_id.getText()))) {
				id = attr_id.getText();
				break;
			}
		}
		return id;
	}
	
	public Element getElemnetByAttrValue(Element element , String childEleName , String attrName , String attrValue) {
		Element ele = null;
		List elements = this.getElements(element, childEleName);
		Attribute attr = null;
		
		for(int i = 0; i < elements.size(); i ++) {
			Element ele1 = (Element) elements.get(i);
			attr = ele1.attribute(attrName);
			if(attrValue != null && attr != null && attrValue.equals(attr.getText())){
				ele = ele1;
				break;
			}
		}
		
		return ele;
		
	}
	
	public String getNameByValue(Element element , String childEleName , String attrName , String attrValue , String desAttr) {
		Element ele = this.getElemnetByAttrValue(element, childEleName, attrName, attrValue);
		Attribute attr = ele.attribute(desAttr);
		return attr.getText();
	}
	
	public List getAllId(Element element , String childEleName) {
		return getAllValueForAttr(element, childEleName, "id");
		
	}
	
	public List getAllValueForAttr(Element element , String childEleName, String attrName) {
		List elements = this.getElements(element, childEleName);
		List values = new ArrayList();
		for(int i = 0; i < elements.size(); i ++) {
			Element ele = (Element) elements.get(i);
			values.add(ele.attributeValue(attrName));
		}
		return values;
		
	}
}
