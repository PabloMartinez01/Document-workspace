import {Injectable} from '@angular/core';
import Swal, {SweetAlertIcon, SweetAlertResult} from 'sweetalert2';


@Injectable({
  providedIn: 'root'
})
export class AlertService {

  public showSuccessAlert(title: string, text: string) {
    Swal.fire(title, text, 'success').then();
  }

  public showErrorAlert(title: string, text: string) {
    Swal.fire(title, text, 'error').then();
  }

  public showConfirmationAlert(onSuccess: () => void) {
    Swal.fire({
      title: 'Are you sure?',
      text: "You won't be able to revert this!",
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#ff5161',
      confirmButtonColor: '#535353',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        onSuccess();
      }
    });
  }

  public showTimerAlert(title: string, text: string, icon: SweetAlertIcon, timer: number,
                        callback: (result: SweetAlertResult<any>) => void) {
    Swal.fire({title, text, icon, timer, timerProgressBar: true, didOpen: () =>  Swal.showLoading()})
      .then(callback);
  }

}
