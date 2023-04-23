package org.winterframework.mybatis.dao.model;

import java.util.Date;
import lombok.Data;

@Data
public class TradeEntity {
    /**
     * 2位IDC+2位业务类型+10位商户号+8位日期+10序列号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 商户号
     */
    private String partner;

    /**
     * 商户内部ID
     */
    private Integer partnerId;

    /**
     * 商户名称
     */
    private String partnerName;

    /**
     * 买家登录账号
     */
    private String buyerLoginno;

    /**
     * 买家内部ID
     */
    private Integer userId;

    /**
     * 买家名字
     */
    private String userName;

    /**
     * 买家用户类型,1:用户 2:商户
     */
    private Byte userType;

    /**
     * 银行类型  ( 供交易订单展现银行卡相关信息（冗余，不查支付单）
     */
    private Integer bankType;

    /**
     * 银行名称   ( 供交易订单展现银行卡相关信息（冗余，不查支付单）
     */
    private String bankName;

    /**
     * 银行账号    ( 供交易订单展现银行卡相关信息（冗余，不查支付单）  25位
     */
    private String bankAcc;

    /**
     * 银行户名     ( 供交易订单展现银行卡相关信息（冗余，不查支付单）
     */
    private String bankAccName;

    /**
     * 卡尾号          ( 供交易订单展现银行卡相关信息（冗余，不查支付单）
     */
    private String cardTail;

    /**
     * 商户的订单创建时间
     */
    private Date partnerCreateTime;

    /**
     * 内部的订单创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 交易结束时间（入账成功时间）
     */
    private Date payTime;

    /**
     * 退款创建时间
     */
    private Date refundCreateTime;

    /**
     * 退款成功时间
     */
    private Date refundSucessTime;

    /**
     * 交易类型 1  普通  2  快速
     */
    private Byte tradeType;

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品描述
     */
    private String body;

    /**
     * 交易金额
     */
    private Long tradeAmount;

    /**
     * 币种类型  默认156
     */
    private String curType;

    /**
     * 交易状态     1未支付   2 支付中  3 成功 4 失败  6 退款中 7 退款成功 8 交易完成 9 关单
     */
    private Byte tradeStatus;

    /**
     * 订单内部状态     1正常2作废 
     */
    private Byte orderInnerStatus;

    /**
     * 是否混合支付     1 是  2 不是
     */
    private Byte mixFlag;

    /**
     * 支付金额
     */
    private Long payAmount;

    /**
     * 支付单号       关联支付系统单号
     */
    private String payTotalNo;

    /**
     * 来源渠道:    1   pay1pay  2手机
     */
    private Byte tradeChannel;

    /**
     * 代理商账号
     */
    private String agentPartner;

    /**
     * 代理商内部ID
     */
    private Integer agentPartnerId;

    /**
     * 代理商计费标准ID
     */
    private String chargeStdNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 内部备注
     */
    private String innerRemak;

    /**
     * 来源ip
     */
    private String sourceIp;

    /**
     * 商品类型
     */
    private Integer goodsType;

    /**
     * 真实银行名称
     */
    private String realBankName;

    /**
     * 卡类型
     */
    private Byte cardType;

    /**
     * 真实银行类型
     */
    private Integer realBankType;

    /**
     * 即时到账 0  担保交易 1 
     */
    private Byte guaranteeType;

    /**
     * 确认收货状态：0 未确认收货，1 已确认收货
     */
    private Byte confirmReceiveStatus;

    /**
     * 确认收货时间 
     */
    private Date sureReceiveTime;

    /**
     * 用户分润金额 
     */
    private Long userProfitAmount;

    /**
     * 结算类型 0合同结算1单笔结算 
     */
    private Byte settleType;

    /**
     * 交易总单号
     */
    private String mainTradeNo;

    /**
     * 外部交易总单号
     */
    private String mainOutTradeNo;

    /**
     * 产品标识：1 虚拟交易，2 实物交易，3 实名类交易
     */
    private Integer productType;

    /**
     * 是否营销账户单:1.是 2.否
     */
    private Byte marketOrder;

    /**
     * 营销金额
     */
    private Long marketAmount;

    /**
     * 营销账户商户号
     */
    private String marketPartner;

    /**
     * 已退款金额（营销账户）
     */
    private Long haveRefundMarketAmount;

    /**
     * 已退款金额
     */
    private Long haveRefundAmount;

    /**
     * 商户订单号
     */
    private String mapOrderId;

    /**
     * 平台商户号
     */
    private String platPartner;

    /**
     * 平台ID
     */
    private String platId;

    /**
     * 业务类型1
     */
    private Integer businessType1;

    /**
     * 业务类型2
     */
    private Integer businessType2;

    /**
     * 业务类型3
     */
    private Integer businessType3;

    /**
     * 经度
     */
    private String riskLongitude;

    /**
     * 纬度
     */
    private String riskLatitude;

    /**
     * MAC地址
     */
    private String riskMac;

    /**
     * IMEI
     */
    private String riskImei;

    /**
     * 充值端口号
     */
    private String riskPort;

    /**
     * 特征串
     */
    private String riskFeatures;

    /**
     * 设备指纹，32位长，预留为64位
     */
    private String fingerPrint;

    /**
     * 商户登录名
     */
    private String partnerLoginName;

    /**
     * 是否存在分润用户 1：存在 0：不存在（默认）
     */
    private Byte haveProfitShare;

    /**
     * 商户自定义字段
     */
    private String attach;

    /**
     * 交易信息，风控、用户等信息，json格式
     */
    private String tradeInfo;

    /**
     * 订单分润信息，json格式
     */
    private String profitParams;

    /**
     * 支付渠道:1 支付系统 2 托管系统
     */
    private Byte payChannel;

    /**
     * 到账状态： 1、未支付 2、支付中 3、支付成功 4、失败
     */
    private Byte arrivalStatus;

    /**
     * 到账时间
     */
    private Date arrivalTime;

    /**
     * 分账标志：0.非分账单 1.分账单
     */
    private Byte profitSharing;

    /**
     * 已分账金额
     */
    private Long haveProfitSharingAmount;

    /**
     * 关单时间
     */
    private Date closeTime;

    /**
     * 通用参数，json格式数据
     */
    private String commonParams;
}