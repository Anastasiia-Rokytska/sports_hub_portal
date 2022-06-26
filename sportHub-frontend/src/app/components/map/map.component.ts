import { Component, EventEmitter, Inject, Input, NgZone, Output, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import * as am4core from '@amcharts/amcharts4/core';
import * as am4charts from '@amcharts/amcharts4/charts';
import am4themes_animated from '@amcharts/amcharts4/themes/animated';
import * as am4maps from "@amcharts/amcharts4/maps";
import am4geodata_worldLow from "@amcharts/amcharts4-geodata/worldLow";
import * as am4plugins_bullets from "@amcharts/amcharts4/plugins/bullets";
import { Team } from 'src/app/classes/team';
import { DynamicService } from 'src/app/dynamic.service';

am4core.useTheme(am4themes_animated);

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent{

  teams = new Array<Team>()

  locationData: any = {
    location: undefined,
    latitude: undefined,
    longitude: undefined
  }

  activeMarker: any

  private chart: am4maps.MapChart | undefined = undefined;

  constructor(
    @Inject(PLATFORM_ID) private platformId: any,
    private zone: NgZone,
    @Inject(Team) teamsInjected: Team[],
    private dynamicService: DynamicService) {
      am4core.options.autoDispose = true;
      this.teams = teamsInjected
      dynamicService.data.clearForm.subscribe((data: boolean) => {
        if (this.activeMarker != undefined) this.activeMarker.disabled = true
      })
    }

  browserOnly(f: () => void) {
    if (isPlatformBrowser(this.platformId)) {
      this.zone.runOutsideAngular(() => {
        f();
      });
    }
  }

  ngAfterViewInit() {
    let teams = this.teams
    let selectedTeamData: any
    // let activeMarker: any

    let chart = am4core.create("chartdiv", am4maps.MapChart);
    chart.geodata = am4geodata_worldLow;
    chart.projection = new am4maps.projections.Miller();

    let polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());
    polygonSeries.exclude = ["AQ"];
    polygonSeries.useGeodata = true;

    let polygonTemplate = polygonSeries.mapPolygons.template;
    polygonTemplate.tooltipText = "{name}";
    polygonTemplate.polygon.fillOpacity = 0.6;

    let hs = polygonTemplate.states.create("hover");
    hs.properties.fill = am4core.color("#D72130");

    let imageSeries = chart.series.push(new am4maps.MapImageSeries());
    imageSeries.mapImages.template.propertyFields.longitude = "longitude";
    imageSeries.mapImages.template.propertyFields.latitude = "latitude";
    imageSeries.mapImages.template.tooltipText = "{title}";
    imageSeries.mapImages.template.propertyFields.url = "url";
    imageSeries.mapImages.template.id = "id"

    let circle = imageSeries.mapImages.template.createChild(am4core.Circle);
    circle.radius = 3;
    circle.propertyFields.fill = "color";
    circle.nonScaling = true;

    let circle2 = imageSeries.mapImages.template.createChild(am4core.Circle);
    circle2.radius = 3;
    circle2.propertyFields.fill = "color";

    let imageTemplate = imageSeries.mapImages.template;
    imageTemplate.propertyFields.longitude = "longitude";
    imageTemplate.propertyFields.latitude = "latitude";
    imageTemplate.nonScaling = true;

    circle2.events.on("inited", function(event){
      if (selectedTeamData == undefined) animateBullet(event.target);
      else selectedTeamData = undefined
    })

    function animateBullet(circle: any) {
        let animation = circle.animate([{ property: "scale", from: 1 / chart.zoomLevel, to: 5 / chart.zoomLevel }, { property: "opacity", from: 1, to: 0 }], 1000, am4core.ease.circleOut);
        animation.events.on("animationended", function(event: any){
          animateBullet(event.target.object);
        })
    }

    imageTemplate.events.on('hit', function(ev){
      selectedTeamData = ev.target.dataItem.dataContext
    })

    chart.seriesContainer.events.on("hit", (ev) => {
      if (this.activeMarker != undefined) this.activeMarker.disabled = true
      if (imageSeries.data.length - 1 == teams.length){
        imageSeries.mapImages.pop()
        imageSeries.data.pop()
      }
      let marker = imageSeries.mapImages.create();
      marker.fill = am4core.color("#D72130")
      this.activeMarker = marker
      if (selectedTeamData == undefined){
        let coords = chart.svgPointToGeo(ev.svgPoint);
        marker.latitude = coords.latitude;
        marker.longitude = coords.longitude;
        imageSeries.data.push({
          "title": "Paris",
          "latitude": marker.latitude,
          "longitude": marker.longitude,
          "color": am4core.color("#D72130")
        })
        createPin(marker, null)
      } else {
        marker.latitude = selectedTeamData.latitude;
        marker.longitude = selectedTeamData.longitude;
        marker.fill = am4core.color("#D72130")
        createPin(marker, selectedTeamData.image)
        this.dynamicService.data.location = undefined
      }
    });

    polygonTemplate.events.on("hit", (ev) => {
      const location: any = ev.target.dataItem.dataContext
      const coord: any = chart.svgPointToGeo(ev.svgPoint)
      this.locationData.location = location.name
      this.locationData.latitude = coord.latitude
      this.locationData.longitude = coord.longitude
      this.dynamicService.data.location = this.locationData
    });

    teams.forEach((team: Team) => {
      imageSeries.data.push({
        "title": team.name,
        "latitude": team.latitude,
        "longitude": team.longitude,
        "color": am4core.color("#D72130"),
        "image": team.image
      })
    })

    function createPin(marker: any, image: any) {
      let pin = marker.createChild(am4plugins_bullets.PinBullet);
      imageSeries.tooltip!.pointerOrientation = "right";
      pin.background.fill = am4core.color("#D72130");
      pin.background.pointerAngle = 90;
      pin.background.pointerBaseWidth = 10;
      pin.image = new am4core.Image();
      pin.image.href = image;
      imageSeries.heatRules.push({
        "target": pin.background,
        "property": "radius",
        "min": 20,
        "max": 40,
        "dataField": "value"
      });
    }
  }
}
