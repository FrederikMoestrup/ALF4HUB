import { setWorldConstructor } from '@cucumber/cucumber';

class CustomWorld {
  constructor() {
    this.page = null;
    this.browser = null;
    this.context = null;
  }
}

setWorldConstructor(CustomWorld);

export default CustomWorld;
