package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This abstract class represents an account with the subclasses: Deposit,
 * PrivateInvestments, and Stocks
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
@XStreamAlias("asset")
public abstract class Asset {

	protected String code;
	protected String label;
	/**
	 * Value represents a different value in each subclass which allows us to
	 * calculate the asset value: In Deposit, it represents the balance In
	 * PrivateInvestment it represents the percentage ownership In Stocks it
	 * represents the number of stocks owned
	 */
	protected double value;

	public Asset(String code, String label) {
		this.code = code;
		this.label = label;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public abstract double getValue();

	public abstract void setValue(Double value);

	public abstract double getTotalWorth();

	public abstract double getAggregateRisk(double totalV);

	public abstract double getRisk();

	public abstract double getAnnualReturn();

	public static ArrayList<Asset> loadAssets() {
		ArrayList<Asset> b = new ArrayList<Asset>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		String query = "select a.assetCode, a.label, a.apr, a.quarterlyDividend, a.baseRateReturn, a.betaMeasure, a.stockSymbol, a.sharePrice, a.baseOmegaMeasure, a.totalValue from Asset a";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				String assetCode = rs.getString("assetCode");
				System.out.println("assetCode = " + assetCode);
				String label = rs.getString("label");
				Double apr = rs.getDouble("apr");
				Double quarterlyDividend = rs.getDouble("quarterlyDividend");
				Double baseRateReturn = rs.getDouble("baseRateReturn");
				Double betaMeasure = rs.getDouble("betaMeasure");
				String stockSymbol = rs.getString("stockSymbol");
				Double sharePrice = rs.getDouble("sharePrice");
				Double baseOmegaMeasure = rs.getDouble("baseOmegaMeasure");
				Double totalValue = rs.getDouble("totalValue");

				if (totalValue > 0) {
					b.add(new PrivateInvestment(assetCode, label, quarterlyDividend, baseRateReturn / 100.0, baseOmegaMeasure, totalValue));
				}
				if (stockSymbol != null) {
					b.add(new Stocks(assetCode, label, quarterlyDividend, baseRateReturn / 100.0, betaMeasure, stockSymbol, sharePrice));
				}
				if (apr > 0) {
					b.add(new Deposit(assetCode, label, apr / 100.0));
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if (rs != null && !rs.isClosed())
				rs.close();
			if (ps != null && !ps.isClosed())
				ps.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return b;
	}
	
}
