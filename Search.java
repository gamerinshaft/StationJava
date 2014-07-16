import java.io.*;
import java.util.*;
import java.awt.*;
public class Search{
  ArrayList<Integer> passedStationNumber = new ArrayList<Integer>();
  int data[][], stationCost[], startNumber, goalNumber, n, minCost;
  String station[], minRoute, fileName, route, goal, start;
  boolean isStartExist = false;
  boolean isGoalExist = false;
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
    StationWay(startNumber,0);
    System.out.println(minRoute);
    System.out.println(minCost);
  }

//--------------------------[DefaultSettings]-------------------------------
  private void DefaultSettings(){
    data = new int[n][n];
    station = new String[n];
    stationCost = new int[n];
  }

//----------------------------[EntryValue]----------------------------------
  private void EntryValue(){
    do{
      AcceptValueFromUser();
      InsertValueAddedByUser();
    }while(!isGoalExist || !isStartExist);
  }

//------------------------[AcceptValueFromUser]-----------------------------
  private void AcceptValueFromUser(){
    try{
      BufferedReader input;
      System.out.println("サンプルデータ内に存在する駅名を入力して下さい。");
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
            isStartExist = true;
          }
          if(isStationGoalName(row)){
            goalNumber = row;
            isGoalExist = true;
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
  private void StationWay(int stationNumber, int cost){
    int i;
    if(isStationGoal(stationNumber)){
      if(isCostLowerThanMinCost(cost)){
        minCost = cost;
        route = "";
        for(i = 0; i < passedStationNumber.size(); i++){
          route += station[passedStationNumber.get(i)] + "->";
        }
        minRoute = route + goal;
      }
      return;
    }

    for(i=0;i<n;i++){
      if(isStationExist(stationNumber,i) && !isStationAlreadyThrough(i)){
        if(isStationCostNullOrHigher(stationNumber, cost, i)){
          stationCost[stationNumber] = cost;
          cost += data[stationNumber][i];
          passedStationNumber.add(stationNumber);
          StationWay(i, cost);
          cost -= data[stationNumber][i];
          passedStationNumber.remove(passedStationNumber.size() - 1);
        }
      }
    }
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

  private boolean isStationCostNullOrHigher(int stationNumber, int cost, int i){
    return stationCost[i] == 0 || cost + data[stationNumber][i] <= stationCost[i];
  }
}