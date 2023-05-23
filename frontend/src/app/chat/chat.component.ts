import { AfterViewInit, Component, OnChanges, OnDestroy, SimpleChanges } from '@angular/core';
import ReactComponent from "./ReactComponent"
import * as React from 'react';
import * as ReactDOM  from 'react-dom';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnChanges, AfterViewInit, OnDestroy{
  title = 'angularreactapp';

  public rootId = 'rootId'

  ngOnChanges(changes: SimpleChanges){
    this.render();
  }

  ngAfterViewInit(){
    this.render();
  }

  ngOnDestroy(){

  }

  private render(){
    ReactDOM.render(React.createElement(ReactComponent), document.getElementById(this.rootId));
  }

}
