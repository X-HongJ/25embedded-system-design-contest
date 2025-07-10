package com.huabing.monitor.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendstatusExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SendstatusExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Boolean value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Boolean value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Boolean value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Boolean value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Boolean value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Boolean value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Boolean> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Boolean> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Boolean value1, Boolean value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Boolean value1, Boolean value2) {
            addCriterion("status not between", value1, value2, "status");
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

        public Criteria andReplyTimeIsNull() {
            addCriterion("reply_time is null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIsNotNull() {
            addCriterion("reply_time is not null");
            return (Criteria) this;
        }

        public Criteria andReplyTimeEqualTo(Date value) {
            addCriterion("reply_time =", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotEqualTo(Date value) {
            addCriterion("reply_time <>", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThan(Date value) {
            addCriterion("reply_time >", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("reply_time >=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThan(Date value) {
            addCriterion("reply_time <", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeLessThanOrEqualTo(Date value) {
            addCriterion("reply_time <=", value, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeIn(List<Date> values) {
            addCriterion("reply_time in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotIn(List<Date> values) {
            addCriterion("reply_time not in", values, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeBetween(Date value1, Date value2) {
            addCriterion("reply_time between", value1, value2, "replyTime");
            return (Criteria) this;
        }

        public Criteria andReplyTimeNotBetween(Date value1, Date value2) {
            addCriterion("reply_time not between", value1, value2, "replyTime");
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