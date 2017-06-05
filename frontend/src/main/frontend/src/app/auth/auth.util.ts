export class AuthUtil {

  private static inputSearchClass: string = 'mdl-textfield';

  private static invalidClass: string = 'is-invalid';

  static hideFooter(): void {
    document.getElementById('footer').style.display = 'none';
  }

  static showFooter(): void {
    document.getElementById('footer').style.display = 'block';
  }

  static showClearForm(): void {
    let inputs = document.getElementsByClassName(this.inputSearchClass);
    for (let i = 0; i < inputs.length; i++) {
      let input = inputs[i];
      input.classList.remove(this.invalidClass);
    }
  }
}
