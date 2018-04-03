package com.prokarma.opa.sql;

public class BidSQL {
	
	public static final String SAVE_BID_SQL = "INSERT INTO opa.bid \r\n" + 
			"            (bid_id, \r\n" + 
			"             bidder_email, \r\n" + 
			"             product_id, \r\n" + 
			"             bid_amount, \r\n" + 
			"             win_status, \r\n" + 
			"             created_date) \r\n" + 
			"VALUES      (opa.bid_id_seq.nextval, \r\n" + 
			"             :bidder_email, \r\n" + 
			"             :product_id, \r\n" + 
			"             :bid_amount, \r\n" + 
			"             'N', \r\n" + 
			"             sysdate) ";
	
	public static final String VIEW_ALL_BIDS_SQL = "SELECT b.bid_id, \r\n" + 
			"       b.bidder_email, \r\n" + 
			"       b.bid_amount, \r\n" + 
			"       b.created_date, \r\n" + 
			"       b.product_id \r\n" + 
			"FROM   opa.bid B, \r\n" + 
			"       opa.product_auction P \r\n" + 
			"WHERE  P.product_id = B.product_id(+) \r\n" + 
			"AND    p.product_id = :productId \r\n" +
			"		order by b.created_date desc";
	
	public static final String GET_COMPLETED_BIDS_SQL="SELECT p.NAME, \r\n" + 
			"       p.min_bid_amount, \r\n" + 
			"       p.product_id,\r\n" + 
			"       u.name bidder_name,\r\n" + 
			"       b.bid_amount,\r\n" + 
			"       b.created_date bid_created_date\r\n" + 
			"       FROM   opa.product_auction p, \r\n" + 
			"       opa.bid b, \r\n" + 
			"       opa.user_opa u \r\n" + 
			"WHERE  b.product_id = p.product_id\r\n" + 
			"       AND u.email = b.bidder_email\r\n" + 
			"       AND b.win_status='Y'\r\n" + 
			"       AND b.bidder_email = :email\r\n" + 
			"       ORDER BY b.created_date DESC";
	
	public static final String FIND_WINNING_BIDS="SELECT bid_id, \r\n" + 
			"       product_id, \r\n" + 
			"       bid_amount \r\n" + 
			"FROM   opa.bid b \r\n" + 
			"WHERE  b.product_id = :productId and b.bid_amount = (SELECT Max(b2.bid_amount) \r\n" + 
			"                       FROM   opa.bid b2 \r\n" + 
			"                       WHERE  b2.product_id = :productId \r\n" + 
			"                              AND b2.win_status = 'N')";
	
	public static final String UPDATE_WINNER_STATUS_TO_Y="UPDATE opa.bid \r\n" + 
			"SET    win_status = 'Y' \r\n" + 
			"WHERE  win_status = 'N' \r\n" + 
			"       AND bid_id = :id";

	public static final String GET_ACTIVE_BIDS_SQL = "SELECT      b.bid_id,                                   \n"
												   + "            p.product_id,                               \n"
												   + "            p.name,                                     \n"
											       + "            p.image,                                    \n"
											       + "            p.min_bid_amount,                           \n"
											       + "            b.bid_amount,                               \n"
											       + "            mb.max_bid_amount                           \n"
											       + "FROM                                                    \n"
											       + "            opa.product_auction p,                      \n"
											       + "            opa.bid b,                                  \n"
											       + "            (SELECT                                     \n"
											       + "                        product_id,                     \n"
											       + "                        MAX(bid_amount) max_bid_amount  \n"
											       + "            FROM        opa.bid                         \n"
											       + "            GROUP BY    product_id) mb                  \n"
											       + "WHERE                                                   \n"
											       + "            p.product_id = b.product_id                 \n"
											       + "        AND                                             \n"
											       + "            b.product_id = mb.product_id                \n"
											       + "        AND                                             \n"
											       + "            p.active = 'Y'                              \n"
											       + "        AND                                             \n"
											       + "            b.bidder_email = :email                       ";

	public static final String GET_BID_FOR_USER_BY_PRODUCT = "SELECT                            \n"
													       + "            bid_id,               \n"
													       + "            bidder_email,         \n"
													       + "            product_id,           \n"
													       + "            bid_amount,           \n"
													       + "            win_status,           \n"
													       + "            created_date          \n"
													       + "FROM        opa.bid               \n"
													       + "WHERE       product_id=:productId \n"
													       + "       AND  bidder_email=:email     ";

	public static final String UPDATE_BID_SQL = "UPDATE		opa.bid					\n"
											  + "SET		bid_amount=:amount, 	\n"
											  + "			created_date=SYSDATE	\n"
											  + "WHERE		bid_id=:bidId			\n";
	
}
