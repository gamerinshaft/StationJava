import java.io.*;
import java.util.*;
import java.awt.*;
public class Search{
  ArrayList<Integer> passedStationNumber = new ArrayList<Integer>();
  int data[][], flag[][], stationCost[], startNumber, goalNumber,n,minCost;
  String station[], goal, start, minRoute, fileName;;
//呼び出しコンストラクタリスト
/*
  main(args[]);
  ⇩
    Search(args[]);
    ⇩
      DefaultSettings();
      EntryValue();
      ⇩
        AcceptValueFromUser();
        InsertValueAddedByUser();
    ⇩
      StationWay(String route, int stationNumber, int cost);
      Confirm();

*/

//-------------------------------[main]-------------------------------------
  public static void main(String args[]){
    new Search(args);
  }
//------------------------------[Search]------------------------------------

  public Search(String args[]){
    fileName = args[0];
    n = Integer.parseInt(args[1]);
    DefaultSettings();
    EntryValue();
    StationWay("",startNumber,0);
    Confirm();
  }

//--------------------------[DefaultSettings]-------------------------------
  private void DefaultSettings(){
    int i;
    int j;
    data = new int[n][n]; // n × n の距離を格納する二次元配列を作成
    flag = new int[n][n];
    station = new String[n]; // n個の駅名を格納する文字列配列を作成
    //flagの初期化
    stationCost = new int[n];
    for(i=0;i<n;i++){
      for(j=0;j<n;j++){
        flag[i][j] = 1;
      }
      stationCost[i] = 0;
    }
  }
//----------------------------[EntryValue]----------------------------------
  private void EntryValue(){
    AcceptValueFromUser();
    InsertValueAddedByUser();
  }

//------------------------[AcceptValueFromUser]-----------------------------
  private void AcceptValueFromUser(){
    try{
      BufferedReader input;
      System.out.println("From?");
      input = new BufferedReader (new InputStreamReader (System.in));
      start = input.readLine( );
      System.out.println("To?");
      input = new BufferedReader (new InputStreamReader (System.in));
      goal = input.readLine( );
    }catch(IOException e){
      e.printStackTrace();
    }
  }
//----------------------[InsertValueAddedByUser]----------------------------
  private void InsertValueAddedByUser(){
    try{
      BufferedReader br;
      FileReader fr;
      int row = 0;
      int i;
      fr = new FileReader(fileName);
      br = new BufferedReader(fr);
      while (br.ready()) {
        String str = br.readLine();
        if(row < n){
          station[row] = str;
          if(isStationStartName(row)){
            startNumber = row;
          }
          if(isStationGoalName(row)){
            goalNumber = row;
          }
        }else{
          String[]tmp = str.split(" ");
          for(i=0; i<n; i++){
            data[row-n][i] = Integer.parseInt(tmp[i]);
           }
        }
        row++;
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  private boolean isStationStartName(int row){
    return start.equals(station[row]) || start.equals(station[row].toLowerCase());
  }
  private boolean isStationGoalName(int row){
    return goal.equals(station[row]) || goal.equals(station[row].toLowerCase());
  }
//----------------------------[StationWay]----------------------------------
  private void StationWay(String route, int stationNumber, int cost){
    if(isStationGoal(stationNumber)){
      System.out.println("this is Goal!");
      if(isCostLowerThanMinCost(cost)){
        minCost = cost;
        System.out.println("this cost is" + minCost);
      }
      return;
    }

    int i;
    for(i=0;i<n;i++){
      if(isStationExist(stationNumber,i)){
        if(!isStationAlreadyThrough(i)){
          route += station[stationNumber] + "->";
          cost += data[stationNumber][i];
          passedStationNumber.add(stationNumber);
          // System.out.println(route);
          // System.out.println("data is" + data[stationNumber][i]);
          StationWay(route, i, cost);
        }
      }
    }
    // System.out.println("るーと"+route);
    // System.out.println("駅番号"+stationNumber);
    // System.out.println("コスト"+cost);
  }

  private boolean isCostLowerThanMinCost(int cost){
    return minCost == 0 || minCost > cost;
  }
  private boolean isStationGoal(int from){
    return from == goalNumber;
  }
  private boolean isStationExist(int i, int j){
    return data[i][j] != 0;
  }
  private boolean isStationAlreadyThrough(int i){
    return passedStationNumber.indexOf(i) > -1;
  }
//----------------------------[confirm]-------------------------------------
  private void Confirm(){
    System.out.println(goalNumber);
    System.out.println(startNumber);
    System.out.println(passedStationNumber.size());
    System.out.println(minRoute);
    System.out.println(String.valueOf(minCost));
  }
//--------------------------------------------------------------------------
}