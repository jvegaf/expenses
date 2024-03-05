import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExpenseListComponent } from './expense-list/expense-list.component';
import { ExpenseViewComponent } from './expense-view/expense-view.component';



@NgModule({
  declarations: [
    ExpenseListComponent,
    ExpenseViewComponent
  ],
  imports: [
    CommonModule
  ]
})
export class ExpensesModule { }
