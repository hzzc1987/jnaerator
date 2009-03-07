/*
	Copyright (c) 2009 Olivier Chafik, All Rights Reserved
	
	This file is part of JNAerator (http://jnaerator.googlecode.com/).
	
	JNAerator is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	JNAerator is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with JNAerator.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ochafik.lang.jnaerator.parser;

import java.util.ArrayList;
import java.util.List;
import com.ochafik.lang.jnaerator.parser.Struct.MemberVisibility;
import com.ochafik.util.string.StringUtils;

public abstract class Declaration extends ModifiableElement {
	protected List<String> nameSpace = new ArrayList<String>();
	protected String name;
	protected TypeRef valueType;
	public MemberVisibility visibility;
	
	final List<Annotation> annotations = new ArrayList<Annotation>();
	
	@Override
	public Element getNextChild(Element child) {
		return getNextSibling(annotations, child);
	}
	@Override
	public Element getPreviousChild(Element child) {
		return getPreviousSibling(annotations, child);
	}

	@Override
	public Declaration clone() {
		return (Declaration) super.clone();
	}
	
	public TypeRef getValueType() {
		return valueType;
	}
	public void setValueType(TypeRef valueType) {
		this.valueType = changeValue(this, this.valueType, valueType);
	}
	
	@Override
	public boolean replaceChild(Element child, Element by) {
		if (child == getValueType()) {
			setValueType((TypeRef) by);
			return true;
		}
		if (replaceChild(annotations, Annotation.class, this, child, by))
			return true;
		
		return false;
	}
	
	public void addAnnotation(Annotation a) {
		if (a != null) {
			annotations.add(a);
			a.setParentElement(this);
		}
	}

	public List<Annotation> getAnnotations() {
		return unmodifiableList(annotations);
	}
	public void setAnnotations(List<Annotation> annotations) {
		changeValue(this, this.annotations, annotations);
	}

	
	public String getModifiersStringPrefix() {
		List<Modifier> mods = getModifiers();
		return StringUtils.implode(mods, " ") + (mods.isEmpty() ? "" : " ");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void addNameSpace(String nameSpace) {
		this.nameSpace.add(0, nameSpace);
	}
	public List<String> getNameSpace() {
		return unmodifiableList(nameSpace);
	}
	public void setNameSpace(List<String> nameSpace) {
		this.nameSpace.clear();
		this.nameSpace.addAll(nameSpace);
	}
	public void setVisibility(MemberVisibility visibility) {
		this.visibility = visibility;
	}
	public MemberVisibility getVisibility() {
		return visibility;
	}
	
}
