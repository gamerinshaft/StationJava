import java.io.*;
import java.util.*;
import java.awt.*;
public class Search{
  ArrayList<Integer> passedStationNumber = new ArrayList<Integer>();
  int data[][], flag[][], stationCost[], startNumber, goalNumber,n;
  String station[], goal, start, minRoute, minCost, fileName;;
//呼び出しコンストラクタリスト
/*
  main();
  ⇩
    Search();
    ⇩
      DefaultSettings();
      EntryValue();
      ⇩
        AcceptValueFromUser();
        InsertValueAddedByUser();
    ⇩
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
          if(start.equals(station[row]) || start.equals(station[row].toLowerCase())){
            startNumber = row;
          }
          if(goal.equals(station[row]) || goal.equals(station[row].toLowerCase())){
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
//--------------------------------------------------------------------------
  private void StationWay(String route, int stationNumber, int cost){
    if(IsStationNumberGoal(stationNumber)){
      System.out.println("this is Goal!");
    }

    int i;
    for(i=0;i<n;i++){
     System.out.println("data is" + data[stationNumber][i]);
    }
    System.out.println("るーと"+route);
    System.out.println("駅番号"+stationNumber);
    System.out.println("コスト"+cost);
  }

  private boolean IsStationNumberGoal(int from){
    return from == goalNumber;
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