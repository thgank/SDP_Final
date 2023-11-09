package Singleton;

public class AirportDispatcher {
    private static AirportDispatcher instance;

    private AirportDispatcher() {
    }

    private static class SingletonHolder{
        private static final AirportDispatcher INSTANCE = new AirportDispatcher();
    }

    public static AirportDispatcher getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
