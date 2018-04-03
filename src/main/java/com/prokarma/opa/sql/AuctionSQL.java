package com.prokarma.opa.sql;

public class AuctionSQL {
	
	public static final String SAVE_AUCTION_SQL = "INSERT INTO opa.product_auction \r\n" + 
			"            (product_id, \r\n" + 
			"             NAME, \r\n" + 
			"             type, \r\n" + 
			"             min_bid_amount, \r\n" + 
			"             expiration_date, \r\n" + 
			"             owner_email, \r\n" + 
			"             active, \r\n" + 
			"             created_date, \r\n" + 
			"             image) \r\n" + 
			"VALUES     (opa.productauction_seq.nextval, \r\n" + 
			"            :name, \r\n" + 
			"            :type, \r\n" + 
			"            :min_price, \r\n" + 
			"            :expired_date, \r\n" + 
			"            :email, \r\n" + 
			"            :active, \r\n" + 
			"            sysdate, \r\n" + 
			"            :image) ";

	public static final String MY_ACTIVE_AUCTION_SQL = "SELECT p.name, \r\n" + 
			"       p.product_id, \r\n" + 
			"       p.TYPE, \r\n" + 
			"       p.image, \r\n" + 
			"       maxBid.max_bid_amount, \r\n" + 
			"       p.min_bid_amount \r\n" + 
			"FROM   opa.product_auction p, \r\n" + 
			"       (SELECT product_id, \r\n" + 
			"               Max(bid_amount) max_bid_amount \r\n" + 
			"        FROM  opa.bid b \r\n" + 
			"        GROUP  BY product_id) maxBid \r\n" + 
			"WHERE  maxBid.product_id(+) = p.product_id \r\n" + 
			"       AND p.owner_email =:email \r\n" + 
			"       AND active = 'Y'"; 

	
	public static final String FIND_ACTIVE_AUCTIONs_BY_EMAIL_NAME_TYPE_SQL="SELECT * \r\n" + 
			"FROM   opa.product_auction \r\n" + 
			"WHERE  owner_email != :email \r\n" + 
			"       AND active = 'Y' ";
	
	public static final String GET_COMPLETEDAUCTIONS_SQL="SELECT p.NAME, \r\n" + 
			"       p.min_bid_amount, \r\n" + 
			"       p.product_id,\r\n" + 
			"       u.name BIDDER_NAME,\r\n" + 
			"       b.bid_amount max_bid_amount\r\n" + 
			"       FROM   opa.product_auction p, \r\n" + 
			"       opa.bid b, \r\n" + 
			"       opa.user_opa u \r\n" + 
			"WHERE  b.product_id = p.product_id\r\n" + 
			"       AND u.email = b.bidder_email\r\n" + 
			"    AND p.active='N'\r\n" + 
			"       AND b.win_status='Y'\r\n" + 
			"       AND p.owner_email = :email";
	
	public static final String GET_PRODUCT_BY_ID_SQL = "SELECT "
			+ "			product_id, "
			+ "			name, "
			+ "			type, "
			+ "			min_bid_amount, "
			+ "			expiration_date, "
			+ "			owner_email, "
			+ "			active, "
			+ "			created_date, "
			+ "			image "
			+ "FROM 	opa.product_auction "
			+ "WHERE 	product_id=:productId";
	
	public static final String GET_PRODUCT_BY_ID_WITH_MAXIMUM_BID_SQL = "SELECT p.product_id, p.name, p.type, p.min_bid_amount, p.expiration_date, p.owner_email, p.active, p.created_date, (\r\n" + 
			"    SELECT image FROM opa.product_auction WHERE product_id = :productId\r\n" + 
			") image, max(b.BID_AMOUNT) max_bid_amount FROM OPA.product_auction p, OPA.bid b WHERE p.product_id = b.PRODUCT_ID(+) AND p.product_id=:productId\r\n" + 
			"GROUP BY p.product_id, p.name, p.type, p.min_bid_amount, p.expiration_date, p.owner_email, p.active, p.created_date";

   public static final String FIND_ACTIVE_PRODUCTS_WITH_PAST_EXPIRATION_DATE_SQL="SELECT product_id \r\n" + 
   		"FROM   opa.product_auction \r\n" + 
   		"WHERE  active = 'Y' \r\n" + 
   		"       AND expiration_date <= sysdate";

   public static final String UPDATE_ACTIVE_STATUS_TO_N="UPDATE opa.product_auction \r\n" + 
	   		"SET    active = 'N' \r\n" + 
	   		"where product_id= :product_id";

}
