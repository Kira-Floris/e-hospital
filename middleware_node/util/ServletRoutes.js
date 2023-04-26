const fs = require("fs");
const path = require("path");

const filePath = path.join(__dirname, "../servlet_routes.json");

class ServletRoutes {
  constructor() {
    this.dataFound = false;
    this.data = null;
  }

  async readFile() {
    try {
      const jsonString = await fs.promises.readFile(filePath, "utf-8");
      this.data = JSON.parse(jsonString);
      this.dataFound = true;
    } catch (e) {
      this.dataFound = false;
    }
  }

  async getAllData() {
    if (!this.dataFound) {
      await this.readFile();
    }
    return this.data;
  }

  async getMainRoute() {
    const data = await this.getAllData();
    return data.main_route;
  }

  async getSubRoute(name) {
    const data = await this.getAllData();
    return data.routes[name];
  }

  async getFullRoute(name){
    const data = await this.getAllData();
    return data.main_route + data.routes[name].url;
  }
}

module.exports = ServletRoutes;
