package com.huabing.monitor.mbg.model;

import java.util.ArrayList;
import java.util.List;

public class AmmeterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AmmeterExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAmmeterIdIsNull() {
            addCriterion("ammeter_id is null");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdIsNotNull() {
            addCriterion("ammeter_id is not null");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdEqualTo(Integer value) {
            addCriterion("ammeter_id =", value, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdNotEqualTo(Integer value) {
            addCriterion("ammeter_id <>", value, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdGreaterThan(Integer value) {
            addCriterion("ammeter_id >", value, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ammeter_id >=", value, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdLessThan(Integer value) {
            addCriterion("ammeter_id <", value, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdLessThanOrEqualTo(Integer value) {
            addCriterion("ammeter_id <=", value, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdIn(List<Integer> values) {
            addCriterion("ammeter_id in", values, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdNotIn(List<Integer> values) {
            addCriterion("ammeter_id not in", values, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdBetween(Integer value1, Integer value2) {
            addCriterion("ammeter_id between", value1, value2, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ammeter_id not between", value1, value2, "ammeterId");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeIsNull() {
            addCriterion("ammeter_app_code is null");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeIsNotNull() {
            addCriterion("ammeter_app_code is not null");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeEqualTo(String value) {
            addCriterion("ammeter_app_code =", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeNotEqualTo(String value) {
            addCriterion("ammeter_app_code <>", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeGreaterThan(String value) {
            addCriterion("ammeter_app_code >", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ammeter_app_code >=", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeLessThan(String value) {
            addCriterion("ammeter_app_code <", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeLessThanOrEqualTo(String value) {
            addCriterion("ammeter_app_code <=", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeLike(String value) {
            addCriterion("ammeter_app_code like", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeNotLike(String value) {
            addCriterion("ammeter_app_code not like", value, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeIn(List<String> values) {
            addCriterion("ammeter_app_code in", values, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeNotIn(List<String> values) {
            addCriterion("ammeter_app_code not in", values, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeBetween(String value1, String value2) {
            addCriterion("ammeter_app_code between", value1, value2, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterAppCodeNotBetween(String value1, String value2) {
            addCriterion("ammeter_app_code not between", value1, value2, "ammeterAppCode");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationIsNull() {
            addCriterion("ammeter_distination is null");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationIsNotNull() {
            addCriterion("ammeter_distination is not null");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationEqualTo(String value) {
            addCriterion("ammeter_distination =", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationNotEqualTo(String value) {
            addCriterion("ammeter_distination <>", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationGreaterThan(String value) {
            addCriterion("ammeter_distination >", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationGreaterThanOrEqualTo(String value) {
            addCriterion("ammeter_distination >=", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationLessThan(String value) {
            addCriterion("ammeter_distination <", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationLessThanOrEqualTo(String value) {
            addCriterion("ammeter_distination <=", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationLike(String value) {
            addCriterion("ammeter_distination like", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationNotLike(String value) {
            addCriterion("ammeter_distination not like", value, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationIn(List<String> values) {
            addCriterion("ammeter_distination in", values, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationNotIn(List<String> values) {
            addCriterion("ammeter_distination not in", values, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationBetween(String value1, String value2) {
            addCriterion("ammeter_distination between", value1, value2, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterDistinationNotBetween(String value1, String value2) {
            addCriterion("ammeter_distination not between", value1, value2, "ammeterDistination");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoIsNull() {
            addCriterion("ammeter_info is null");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoIsNotNull() {
            addCriterion("ammeter_info is not null");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoEqualTo(String value) {
            addCriterion("ammeter_info =", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoNotEqualTo(String value) {
            addCriterion("ammeter_info <>", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoGreaterThan(String value) {
            addCriterion("ammeter_info >", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoGreaterThanOrEqualTo(String value) {
            addCriterion("ammeter_info >=", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoLessThan(String value) {
            addCriterion("ammeter_info <", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoLessThanOrEqualTo(String value) {
            addCriterion("ammeter_info <=", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoLike(String value) {
            addCriterion("ammeter_info like", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoNotLike(String value) {
            addCriterion("ammeter_info not like", value, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoIn(List<String> values) {
            addCriterion("ammeter_info in", values, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoNotIn(List<String> values) {
            addCriterion("ammeter_info not in", values, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoBetween(String value1, String value2) {
            addCriterion("ammeter_info between", value1, value2, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andAmmeterInfoNotBetween(String value1, String value2) {
            addCriterion("ammeter_info not between", value1, value2, "ammeterInfo");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNull() {
            addCriterion("file_id is null");
            return (Criteria) this;
        }

        public Criteria andFileIdIsNotNull() {
            addCriterion("file_id is not null");
            return (Criteria) this;
        }

        public Criteria andFileIdEqualTo(Integer value) {
            addCriterion("file_id =", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotEqualTo(Integer value) {
            addCriterion("file_id <>", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThan(Integer value) {
            addCriterion("file_id >", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("file_id >=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThan(Integer value) {
            addCriterion("file_id <", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdLessThanOrEqualTo(Integer value) {
            addCriterion("file_id <=", value, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdIn(List<Integer> values) {
            addCriterion("file_id in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotIn(List<Integer> values) {
            addCriterion("file_id not in", values, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdBetween(Integer value1, Integer value2) {
            addCriterion("file_id between", value1, value2, "fileId");
            return (Criteria) this;
        }

        public Criteria andFileIdNotBetween(Integer value1, Integer value2) {
            addCriterion("file_id not between", value1, value2, "fileId");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}