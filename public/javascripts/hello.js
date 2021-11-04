$(document).ready(function() {
  //setup multiple rows of colours, can also add and remove while spinning but overall this is easier.
  initWheel();

  $('button').on('click', function(){
    var outcome = parseInt($('input').val());
    spinWheel(outcome);
  });
});

function initWheel(){
  var $wheel = $('.roulette-wrapper .wheel'),
      row = "";

  row += "<div class='row'>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize1.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize14.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize2.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize13.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize3.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize12.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize4.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize0.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize11.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize5.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize10.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize6.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize9.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize7.webp' alt=''>" +
      "<\/div>";
  row += "  <div class='prize'>" +
      "<img src='/assets//images/prize8.webp' alt=''>" +
      "<\/div>";
  row += "<\/div>";

  for(var x = 0; x < 29; x++){
    $wheel.append(row);
  }
}

function spinWheel(roll){
  var prizeTotal = 15,
      prizeWidth = 150,
      prizeMargin = 20;

  var $wheel = $('.roulette-wrapper .wheel'),
      order = [0, 11, 5, 10, 6, 9, 7, 8, 1, 14, 2, 13, 3, 12, 4],
      position = order.indexOf(roll);

  //determine position where to land
  var rows = 5,
      prize = prizeWidth + prizeMargin * 2,
      landingPosition = (rows * prizeTotal * prize) + (position * prize);

  var randomize = Math.floor(Math.random() * prizeWidth) - (prizeWidth/2);

  landingPosition = landingPosition + randomize;

  var object = {
    x: Math.floor(Math.random() * 50) / 100,
    y: Math.floor(Math.random() * 20) / 100
  };

  $wheel.css({
    'transition-timing-function':'cubic-bezier(0,'+ object.x +','+ object.y + ',1)',
    'transition-duration':'6s',
    'transform':'translate3d(-'+landingPosition+'px, 0px, 0px)'
  });

  setTimeout(function(){
    $wheel.css({
      'transition-timing-function':'',
      'transition-duration':'',
    });

    var resetTo = -(position * prize + randomize);
    $wheel.css('transform', 'translate3d('+resetTo+'px, 0px, 0px)');
  }, 6 * 1000);
}

