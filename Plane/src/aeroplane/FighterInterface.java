package aeroplane;

public interface FighterInterface {
public int[] reserve(String []names);//预定航班座位
public boolean cancel(int bookingNumber);//取消预定座位
public Passenger[] getPassengerList();//返回旅客列表
}
