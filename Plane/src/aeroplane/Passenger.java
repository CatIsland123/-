package aeroplane;

public class Passenger implements PassengerInterface {
 private String names;
 private int bookingNumber;
 private int rows;
 private int seatPosition;
 
 public Passenger(String names,int bookingNumber,int rows, int seatPosition){//构造函数给变量赋值
		this.names=names;
		this.bookingNumber=bookingNumber;
		this.rows=rows;
		this.seatPosition=seatPosition;
	}

	@Override
	public String getName() {//获取旅客名字
		
		return names;
	}

	@Override
	public int getBookingNum() {
		
		return bookingNumber;
	}

	@Override
	public int getrow() {
	
		return rows;
	}

	@Override
	public int getSeatPosition() {
	
		return seatPosition;
	}

}
