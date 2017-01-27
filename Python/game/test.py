import pygame
import time
import random

pygame.init()

display_width = 800
display_height = 600

#RGB
black = (0,0,0)  #color of display (red, yellow, blue)
white = (255,255,255)
red = (255,0,0)

car_width = 73

gameDisplay = pygame.display.set_mode((display_width,display_height))  # Game's resolution (tuple)

pygame.display.set_caption('A bit Racey')         # Game's name

clock = pygame.time.Clock()                       # Game's clock (fps)

carImg = pygame.image.load('racecar.png')         # Image import


def things(thingx, thingy, thingw, thingh, color):
    pygame.draw.rect(gameDisplay, color, [thingx, thingy, thingw, thingh])
    
    

def car(x,y):
    gameDisplay.blit(carImg, (x,y))               # where to blit it (display it)

def text_objects(text, font):
    textSurface = font.render(text, True, black)
    return textSurface, textSurface.get_rect()

def message_display(text):
    largeText = pygame.font.Font('freesansbold.ttf',70)  #font and size
    TextSurf, TextRect = text_objects(text, largeText)
    TextRect.center = ((display_width/2), (display_height/2))
    gameDisplay.blit(TextSurf, TextRect)

    pygame.display.update()

    time.sleep(2) # sleep the game 2 seconds

    game_loop()
    
def crash():
    message_display('You Crashed')

    




def game_loop():
    x = (display_width * 0.45)
    y = (display_height * 0.8)

    x_change = 0

    thing_startx = random.randrange(0, display_width)
    thing_starty = -600 #600 pixels out of the screen
    thing_speed = 7
    thing_width = 100
    thing_height = 100

    gameExit = False # didn't crash yet

    while not gameExit:

        for event in pygame.event.get():               # pressing keys, mouse, everything on screen (creates a list fps)
            if event.type == pygame.QUIT:
                pygame.quit()
                quit()

            if event.type == pygame.KEYDOWN:           # A key was pushed down
                if event.key == pygame.K_LEFT:
                    x_change = -5
                if event.key == pygame.K_RIGHT:
                    x_change = 5

            if event.type == pygame.KEYUP:  #key was released
                if event.key == pygame.K_LEFT or event.key == pygame.K_RIGHT:
                    x_change = 0
                    
                    #if the user crossed over the edge ->CRASH


        x += x_change
        gameDisplay.fill(white)


        things(thing_startx, thing_starty, thing_width, thing_height, black)
        thing_starty += thing_speed
        
        car(x,y)

        if x > display_width- car_width or x < 0:
            crash()

        if thing_starty > display_height:
            thing_starty = 0 - thing_height
            thing_startx = random.randrange(0,display_width)
        
        pygame.display.update()  #updates the display
        clock.tick(60)           #fps

game_loop()
pygame.quit()
quit() #quit from python

    
    

