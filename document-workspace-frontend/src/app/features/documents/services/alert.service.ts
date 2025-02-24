import {Injectable} from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  public showSuccessAlert(title: string, text: string) {
    return Swal.fire(title, text, 'success');
  }

  public showErrorAlert(title: string, text: string) {
    return Swal.fire(title, text, 'error');
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

}
